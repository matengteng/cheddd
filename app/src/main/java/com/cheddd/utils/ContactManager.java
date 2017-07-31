package com.cheddd.utils;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.cheddd.bean.CallLogInfo;
import com.cheddd.bean.ContactBean;
import com.cheddd.bean.ContactGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by mateng on 16/8/17.
 */
public class ContactManager {

    private TreeMap<String, ArrayList<ContactBean>> contacts;

    private List<ContactGroup> contactGroups;

    private static ContactManager instance;

    private Context context;

    public static synchronized ContactManager getInstance(Context context) {

        if (instance == null) {
            instance = new ContactManager(context);
        } else {
            instance.context = context;
        }
        return instance;
    }

    private ContactManager(Context context) {
        this.context = context;
    }

    /**
     * 获取联系人回调
     */
    public interface CallBack {
        void getContacts(List<ContactGroup> contactGroups);
    }


    /**
     * 往数据库中新增通话记录
     *
     * @param calllog
     */
    public void addCallLogs(CallLogInfo calllog) {
        ContentValues values = new ContentValues();
        values.clear();
        values.put(CallLog.Calls.CACHED_NAME, calllog.getName());
        values.put(CallLog.Calls.NUMBER, calllog.getNumber());
        Log.d("ContactManager", "calllog.getType():" + calllog.getType());
        values.put(CallLog.Calls.TYPE, calllog.getType());
        values.put(CallLog.Calls.DATE, calllog.getDate());
        values.put(CallLog.Calls.NEW, "0");// 0已看1未看 ,由于没有获取默认全为已读
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
    }

    public static class Contact {

        private String displayname;

        private List<String> phoneNum;

        public String getDisplayname() {
            return displayname;
        }

        public void setDisplayname(String displayname) {
            this.displayname = displayname;
        }

        public List<String> getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(List<String> phoneNum) {
            this.phoneNum = phoneNum;
        }
    }

    /**
     * 初始化数据库查询参数,获取数据
     */
    public static List<Contact> getContacts(Context context) {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                "phonebook_label"
        };

        Map<String, Contact> map = new HashMap<>();

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY + " COLLATE LOCALIZED asc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst(); // 游标移动到第一项
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String name = cursor.getString(1);
                String number = cursor.getString(2);

                if (map.get(name) == null) {
                    Contact contact = new Contact();
                    contact.setDisplayname(name);
                    List<String> nums = new ArrayList<>();
                    nums.add(number);
                    contact.setPhoneNum(nums);
                    map.put(name,contact);
                } else {
                    map.get(name).getPhoneNum().add(number);
                }
            }
            cursor.close();
        }
        return new ArrayList<Contact>(map.values());
    }
   /* *//**
     * 初始化数据库查询参数,获取数据
     *//*
    public List<ContactGroup> getContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI; // 联系人Uri；
        // 查询的字段
        String[] projection = {ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.DATA1,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                "phonebook_label"
        };
        contacts = new TreeMap<>(new Comparator<String>() {
            *//*
             * int compare(Object o1, Object o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。
             *//*
            public int compare(String o1, String o2) {
                if (o1.equals("#")) {
                    return o2.compareTo(o1);
                }
                //指定排序器按照升序排列
                return o1.compareTo(o2);
            }
        });

        contactGroups = new ArrayList<>();

        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY + " COLLATE LOCALIZED asc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst(); // 游标移动到第一项
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String name = cursor.getString(1);
                String number = cursor.getString(2);
                String sortKey = cursor.getString(3);
                int contactId = cursor.getInt(4);
                Long photoId = cursor.getLong(5);
                String lookUpKey = cursor.getString(6);
                String phoneBookLabel = cursor.getString(7);
                ContactBean contact = new ContactBean();
                contact.setDisplayName(name);
                contact.setPhoneNum(number);
               *//* contact.setContactId(contactId);
                contact.setSortKey(sortKey);
                contact.setPhotoId(photoId);
                contact.setLookUpKey(lookUpKey);
                contact.setPhoneBookLabel(phoneBookLabel);*//*

               // Log.d("ContactManager", contact.toString());
                if (contacts.get(phoneBookLabel) == null) {
                    contacts.put(phoneBookLabel, new ArrayList<ContactBean>());
                }

                contacts.get(phoneBookLabel).add(contact);
            }
            for (ArrayList<ContactBean> contactList : contacts.values()
                    ) {
                ContactGroup group = new ContactGroup();
                group.setContact_childs(contactList);
                group.setSort_key(contactList.get(0).getPhoneBookLabel());
                contactGroups.add(group);
            }

            cursor.close();
        }
        return contactGroups;
    }*/

    /**
     * 清空联系人
     */
    public int removeAllContacts() {
        Cursor contactsCur = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        int result = context.getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, null, null);
        contactsCur.close();
        return result;
    }

    /**
     * 删除所有的通话记录
     *
     * @return
     */
    public int removeAllCallLog() {
        ContentResolver resolver = context.getContentResolver();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return 0;
        }
        int i = resolver.delete(CallLog.Calls.CONTENT_URI, null, null);
        return i;
    }

    /**
     * 删除所有的未接电话
     *
     * @return
     */
    public int removeAllMissed() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            return 0;
        }
        ContentResolver resolver = context.getContentResolver();
        int i = resolver.delete(CallLog.Calls.CONTENT_URI, "type = 3", null);
        return i;
    }

    /**
     * 获取所有的通话记录
     *
     * @return
     */
    public ArrayList<CallLogInfo> getCallLog() {

        ArrayList<CallLogInfo> infos = new ArrayList();
        ContentResolver cr = context.getContentResolver();
        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projection = new String[]{CallLog.Calls.NUMBER, CallLog.Calls.DATE,
                CallLog.Calls.TYPE, CallLog.Calls.CACHED_NAME};
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
            Cursor cursor = cr.query(uri, projection, null, null, CallLog.Calls.DATE + " COLLATE LOCALIZED desc");
            while (cursor.moveToNext()) {
                String number = cursor.getString(0);
                long date = cursor.getLong(1);
                int type = cursor.getInt(2);
                String name = cursor.getString(3);
                CallLogInfo info = new CallLogInfo();
                info.setDate(date);
                info.setName(name);
                info.setNumber(number);
                info.setType(type);
                infos.add(info);

                //  Log.d("ContactManager", info.toString());
            }
            cursor.close();
        }
        return infos;
    }
}
