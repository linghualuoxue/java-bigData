package com.bj.sxt.com.bj.sxt.transformer.model.base;

import com.bj.sxt.com.bj.sxt.util.GlobalConstants;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/1/19.
 */
public class PlatformDimesion extends BaseDimension{

    private int id;
    private String platformName;


    public static List<PlatformDimesion> buildList(String platformName){
        List<PlatformDimesion> list = new ArrayList<PlatformDimesion>();
        if(StringUtils.isBlank(platformName)){
            platformName = GlobalConstants.DEFAULT_VALUE;
        }
        list.add(new PlatformDimesion(platformName));
        list.add(new PlatformDimesion(GlobalConstants.VALUE_OF_ALL));
        return list;
    }

    public PlatformDimesion() {
    }

    public PlatformDimesion(String platformName) {
        this.platformName = platformName;
    }

    public PlatformDimesion(int id, String platformName) {
        this.id = id;
        this.platformName = platformName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }



    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.platformName);
    }

    public void readFields(DataInput input) throws IOException {
        this.id = input.readInt();
        this.platformName = input.readUTF();
    }

    public int compareTo(BaseDimension o) {
        if (this == o)return 0;
        PlatformDimesion other = (PlatformDimesion)o;
        int tmp = Integer.compare(this.id,other.getId());
        if(tmp != 0) {
         return tmp;
        }
        return this.platformName.compareTo(other.getPlatformName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result =1;
        result = prime*result +id;
        result = prime*result+((this.platformName==null)?0:this.platformName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null)return false;
        PlatformDimesion other = (PlatformDimesion)obj;
        if(this.getClass() != other.getClass())return false;
        if(this.id != other.getId())return false;
        if(this.platformName==null){
            if(other.getPlatformName()!=null)return false;
        }else{
            if(!this.platformName.equals(other.getPlatformName())){
              return false;
            }
        }
      return true;
    }
}
