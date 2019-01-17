package model;

import java.util.Arrays;
import java.util.List;

public enum UserRole {
    MASTER("master"), MANAGER("manager"), CLIENT("client");
    private String value;

    UserRole(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue(){
        return value;
    }
    public UserRole getUserRole(String value){
       List<UserRole> userRoles = Arrays.asList(UserRole.values());
       for(UserRole userRole: userRoles){
           if(userRole.value.equals(value)){
               return userRole;
           }
       }
       return null;
    }
}
