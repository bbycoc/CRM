package com.bby.crm.workbench.service.impl;

import com.bby.crm.utils.DateTimeUtil;
import com.bby.crm.utils.SqlSessionUtil;
import com.bby.crm.utils.UUIDUtil;
import com.bby.crm.workbench.dao.*;
import com.bby.crm.workbench.domain.*;
import com.bby.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServiceImpl implements ClueService {
    private ClueDao clueDao= SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private ClueActivityRelationDao clueActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ClueRemarkDao clueRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    private CustomerDao customerDao= SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private CustomerRemarkDao remarkDao= SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    private ContactsDao contactsDao=SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao=SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao=SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);

    private TranDao tranDao= SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao=SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    private ActivityDao activityDao=SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);


    public boolean saveClue(Clue c) {
        int nums=clueDao.saveClue(c);
        if (nums!=1){
            return false;
        }else {
            return true;
        }
    }

    public int getTotal(Map map1) {
        return clueDao.getTotal(map1);
    }

    public List<Clue> getClueList(Map map1) {
        List<Clue> clueList=clueDao.getClueList(map1);
        return clueList;
    }

    public Clue getDetail(String id) {
        return clueDao.getDetail(id);
    }

    public boolean convert(String clueId, Tran t, String createBy) {
        String createTime= DateTimeUtil.getSysTime();
        boolean flag=true;
        //????????????id???????????????
        Clue clue=clueDao.getClueById(clueId);
        //????????????????????????????????????
        String company=clue.getCompany();
        Customer cus=customerDao.getCustomByName(company);
        if (cus==null){
            //??????????????????????????????
            cus=new Customer();
            cus.setAddress(clue.getAddress());
            cus.setContactSummary(clue.getContactSummary());
            cus.setCreateBy(createBy);
            cus.setCreateTime(createTime);
            cus.setDescription(clue.getDescription());
            cus.setId(UUIDUtil.getUUID());
            cus.setName(company);
            cus.setNextContactTime(clue.getNextContactTime());
            cus.setWebsite(clue.getWebsite());
            cus.setPhone(clue.getPhone());
            cus.setOwner(clue.getOwner());
            int count1=customerDao.addCustomer(cus);
            if (count1!=1){
                flag=false;
            }
        }
        //????????????????????????????????????????????????
        Contacts contacts=new Contacts();
        contacts.setAddress(clue.getAddress());
        contacts.setAppellation(clue.getAppellation());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setEmail(clue.getEmail());
        contacts.setFullname(clue.getFullname());
        contacts.setJob(clue.getJob());
        contacts.setId(UUIDUtil.getUUID());
        contacts.setSource(clue.getSource());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setMphone(clue.getMphone());
        contacts.setOwner(clue.getOwner());
        contacts.setCustomerId(cus.getId());
        int count2=contactsDao.addContact(contacts);
        if (count2!=1){
            flag=false;
        }
        //??????????????????????????????????????????????????????
        List<ClueRemark> clueRemarks=clueRemarkDao.getcRList(clueId);
        CustomerRemark customerRemark=new CustomerRemark();
        ContactsRemark contactsRemark=new ContactsRemark();
        for (ClueRemark clueRemark:clueRemarks){
            String noteContent=clueRemark.getNoteContent();
            //????????????????????????????????????
            customerRemark.setNoteContent(noteContent);
            customerRemark.setId(UUIDUtil.getUUID());
            customerRemark.setEditFlag("0");
            customerRemark.setCreateTime(createTime);
            customerRemark.setCreateBy(createBy);
            customerRemark.setCustomerId(cus.getId());
            int count3=remarkDao.addRemark(customerRemark);

            ////???????????????????????????????????????
            contactsRemark.setNoteContent(noteContent);
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setEditFlag("0");
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setCreateBy(createBy);
            contactsRemark.setContactsId(contacts.getId());
            int count4=contactsRemarkDao.addContactsRemark(contactsRemark);
            if (count3!=1&&count4!=1){
                flag=false;
            }
            }
            //????????????????????????????????????????????????????????????????????????????????????
            //??????????????????????????????????????????????????????????????????????????????????????????
            List<ClueActivityRelation> clueActivityRelationList = clueActivityRelationDao.getListByClueId(clueId);
            //????????????????????????????????????????????????????????????
            for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
            //???????????????????????????????????????????????????????????????id
                String activityId = clueActivityRelation.getActivityId();
                //???????????????????????????????????????????????????
                ContactsActivityRelation contactsActivityRelation=new ContactsActivityRelation();
                contactsActivityRelation.setActivityId(activityId);
                contactsActivityRelation.setId(UUIDUtil.getUUID());
                contactsActivityRelation.setContactsId(contacts.getId());
                int count5=contactsActivityRelationDao.addCar(contactsActivityRelation);
                if (count5!=1){
                    flag=false;
                }
            }
            //????????????
            if (t!=null){
                t.setSource(clue.getSource());
                t.setOwner(clue.getOwner());
                t.setNextContactTime(clue.getNextContactTime());
                t.setDescription(clue.getDescription());
                t.setCustomerId(cus.getId());
                t.setContactSummary(contacts.getContactSummary());
                t.setContactsId(contacts.getId());
                int count6=tranDao.add(t);
                if (count6!=1){
                    flag=false;
                }
                //?????????????????????????????????????????????
                TranHistory tranHistory=new TranHistory();
                tranHistory.setCreateBy(createBy);
                tranHistory.setCreateTime(createTime);
                tranHistory.setExpectedDate(t.getExpectedDate());
                tranHistory.setId(UUIDUtil.getUUID());
                tranHistory.setStage(t.getStage());
                tranHistory.setMoney(t.getMoney());
                tranHistory.setTranId(t.getId());
                int count7=tranHistoryDao.add(tranHistory);
                if (count7!=1){
                    flag=false;
                }
            }
            //??????????????????
            for (ClueRemark clueRemark1:clueRemarks){
                int count8=clueRemarkDao.del(clueRemark1);
                if (count8!=1){
                    flag=false;
                }
            }
            //????????????????????????????????????
            for(ClueActivityRelation clueActivityRelation : clueActivityRelationList){
                int count9=clueActivityRelationDao.del(clueActivityRelation);
                if (count9!=1){
                    flag=false;
                }
            }
            //????????????
            int count10=clueDao.del(clueId);
            if (count10!=1){
                flag=false;
            }
        return flag;
    }

    public List<Activity> getActivityList(String clueId) {
        return clueDao.getActivityList(clueId);
    }

    public int delActivity(String activityId, String clueId) {
        Map map=new HashMap();
        map.put("activityId",activityId);
        map.put("clueId",clueId);
        return clueActivityRelationDao.delById(map);
    }

    public List<Activity> searchActivityByName(String name, String clueId) {
        Map map=new HashMap();
        map.put("name",name);
        map.put("clueId",clueId);
        return activityDao.search(map);
    }

    public boolean bind(String cid, String[] aids) {
        boolean flag=true;
        for (String aid:aids){
            ClueActivityRelation car=new ClueActivityRelation();
            car.setActivityId(aid);
            car.setClueId(cid);
            car.setId(UUIDUtil.getUUID());
            int count=clueActivityRelationDao.bind(car);
            if (count!=1){
                flag=false;
            }
        }
        return flag;
    }
}
