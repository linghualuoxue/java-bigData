package com.bj.sxt.com.bj.sxt.transformer.model.base;

import com.bj.sxt.com.bj.sxt.util.GlobalConstants;
import org.apache.commons.lang.StringUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/1/17.
 */
public class BrowserDimension extends BaseDimension{

    private int id;
    private String browserName;
    private String browserVersion;

    public BrowserDimension() {
    }

    public BrowserDimension(String browserName, String browserVersion) {
        this.browserName = browserName;
        this.browserVersion = browserVersion;
    }

    public void clean(){
        this.id=0;
        this.browserName="";
        this.browserVersion = "";
    }

    public static BrowserDimension newInstance(String browserName,String browserVersion){
        return new BrowserDimension(browserName,browserVersion);
    }

    /**
     * 构建多个浏览器维度信息对象集合
     * @param browserName
     * @param browserVersion
     * @return
     */
    public static List<BrowserDimension> buildList(String browserName, String browserVersion){
        List<BrowserDimension> list = new ArrayList<BrowserDimension>();
        if(StringUtils.isBlank(browserName)){
           browserName = GlobalConstants.DEFAULT_VALUE;
            browserVersion = GlobalConstants.DEFAULT_VALUE;
        }
        if(StringUtils.isBlank(browserVersion)){
            browserVersion = GlobalConstants.DEFAULT_VALUE;
        }
        list.add(newInstance(browserName,GlobalConstants.VALUE_OF_ALL));
        list.add(newInstance(browserName,browserName));
        return list;
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }

    @Override
    public int compareTo(BaseDimension o) {
        if(this == o){
            return 0;
        }
        BrowserDimension other = (BrowserDimension)o;
        int tmp = Integer.compare(this.id,other.getId());
        if(tmp!=0){
            return tmp;
        }
        tmp = this.browserName.compareTo(other.getBrowserName());
        if(tmp!=0)return tmp;
        return this.browserVersion.compareTo(other.getBrowserVersion());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result+((this.browserName==null)?0:this.browserName.hashCode());
        result = prime*result +((this.browserVersion==null)?0:this.browserVersion.hashCode());
        result = prime*result+this.id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)return true;
        if(obj==null)return false;
        if(getClass()!=obj.getClass())return false;
        if(obj instanceof  BrowserDimension){
           BrowserDimension other = (BrowserDimension)obj;
            if(this.browserName == null){
                if(other.browserName!=null)return false;
            }else if(!this.browserName.equals(other.browserName))return false;

            if(this.browserVersion == null){
                if(other.browserVersion!=null)return false;
            }else if(!this.browserVersion.equals(other.browserVersion))return false;

            if(this.id != other.getId())return false;
            return true;
        }
        return false;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(id);
        out.writeUTF(browserName);
        out.writeUTF(browserVersion);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this.id = input.readInt();
        this.browserName = input.readUTF();
        this.browserVersion = input.readUTF();
    }

}
