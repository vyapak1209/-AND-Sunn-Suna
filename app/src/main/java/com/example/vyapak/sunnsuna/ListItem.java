package com.example.vyapak.sunnsuna;

/**
 * Created by VYAPAK on 5/23/2018.
 */

public class ListItem {
    
   private String head;
   private String desc;
   
   public ListItem(String head, String desc){
       this.head = head;
       this.desc = desc;
   }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }
}
