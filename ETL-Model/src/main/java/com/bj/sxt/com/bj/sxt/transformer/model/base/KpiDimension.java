package com.bj.sxt.com.bj.sxt.transformer.model.base;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by Administrator on 2017/1/19.
 */
public class KpiDimension extends BaseDimension{

    private int id;
    private  String kpiName;

    public KpiDimension() {
    }

    public KpiDimension(int id, String kpiName) {
        this.id = id;
        this.kpiName = kpiName;
    }

    public KpiDimension(String kpiName) {
        this.kpiName = kpiName;
    }


    @Override
    public int compareTo(BaseDimension o) {
       if(this == o)return 0;
        KpiDimension other = (KpiDimension)o;
        int tmp = Integer.compare(this.id,other.getId());
        if(tmp!=0)return tmp;
        return this.kpiName.compareTo(other.kpiName);
    }

    @Override
    public void write(DataOutput out) throws IOException {

        out.writeInt(this.id);
        out.writeUTF(this.kpiName);
    }

    @Override
    public void readFields(DataInput input) throws IOException {
        this.id = input.readInt();
        this.kpiName = input.readUTF();
    }

    @Override
    public int hashCode() {
       int prime = 31;
        int result = 1;
        result = prime*result+id;
        result = prime*result+((this.kpiName==null)?0:this.kpiName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
       if(this === obj){
           return true;
       }
        if (obj == null) {
            return false;
        }
        if(getClass() !=obj.getClass()){
            return  false;
        }
        KpiDimension other = (KpiDimension)obj;
        if(this.id!=other.getId()){
            return false;
        }
        if(this.kpiName==null){
            if(other.getKpiName()!=null)return false;
        }else{
            if(!this.kpiName.equals(other.getKpiName()))return false;
        }
        return true;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKpiName() {
        return kpiName;
    }

    public void setKpiName(String kpiName) {
        this.kpiName = kpiName;
    }
}
