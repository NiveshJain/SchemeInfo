package com.example.schemes.model;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by LNJPC on 04-03-2016.
 */



public class Scheme {

    public int getScheme_id() {
        return scheme_id;
    }

    public void setScheme_id(int scheme_id) {
        this.scheme_id = scheme_id;
    }

    private int drugId ;
    private String brandName ;
    private String name ;
    private String manufacturer ;
  //  private String mrp ;
    private String scheme ;
  //  private String startDate;
    private Date endDate;
    private String is_fav;
    private int scheme_id ;

    public Scheme () {}

    public Scheme(int scheme_id,String brandName,int drugId , String name, String manufacturer, String scheme, Date endDate, String is_fav) {
        this.drugId = drugId;
        this.brandName = brandName;
        this.name = name;
        this.manufacturer = manufacturer;
        this.scheme = scheme;

        this.endDate = endDate;
        this.is_fav = is_fav;
       this.scheme_id = scheme_id;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

   /* public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

       public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }*/

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }


    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getIs_fav() {
        return is_fav;
    }

    public void setIs_fav(String is_fav) {
        this.is_fav = is_fav;
    }

   public static Comparator<Scheme> EndDateAscComparator = new Comparator<Scheme> (){


       @Override
       public int compare(Scheme lhs, Scheme rhs) {
          return lhs.getEndDate().compareTo(rhs.getEndDate());
       }
   };

    public static Comparator<Scheme> EndDateDescComparator = new Comparator<Scheme> (){


        @Override
        public int compare(Scheme lhs, Scheme rhs) {
            return rhs.getEndDate().compareTo(lhs.getEndDate());
        }
    };

}
