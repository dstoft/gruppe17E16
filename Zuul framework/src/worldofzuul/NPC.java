/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuul;

import java.util.UUID;

/**
 *
 * @author emil_daniel
 */
public class NPC {

    String name;
    UUID uuid = UUID.randomUUID();
    int rid;
    

    public void npc(String name, int rid) {
        this.name = name;
        this.rid = rid;
    }
    
    Inventory inventory = new Inventory();
    
    public String getNPCInv(){
        return inventory.showInventory();
    }
    
    public UUID CreateItem (String name, int weight, int rid) {
    
        return inventory.addItem(weight, name, rid);
}
    
    public void RemoveItem (int x){
    
    inventory.remItem(x);
          
    }
            
    public  UUID GetUUID (int x) {
    
        return inventory.getUUIDFromInvPos(x);
       
    }
    
   public String getItemInfo(){
   
   return inventory.getItemInfo(rid); 
   } 
   
   
   public UUID setItemInfo(String info) {
   
   return inventory.setItemInfo(info);
}
    
}
