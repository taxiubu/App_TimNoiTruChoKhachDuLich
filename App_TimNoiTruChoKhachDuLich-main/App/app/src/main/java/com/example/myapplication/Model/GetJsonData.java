package com.example.myapplication.Model;

import android.os.AsyncTask;
import android.util.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GetJsonData extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "GetJsonData";
    private String result = "";
    private String urlString;
    public GetJsonData(String urlString) {
        this.urlString = urlString;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        Document document=null;
        try {
            document= Jsoup.connect(urlString).get();
            result= document.html();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }
    protected List<Residence> getResidenceList(){
        List<Residence> residenceList = new ArrayList<>();
        Document document= Jsoup.parse(result);
        if(document!=null){
            Element ajaxsrwrap=document.selectFirst("div#ajaxsrwrap");
            Element hotellist_inner= ajaxsrwrap.selectFirst("div#hotellist_inner");
            Elements hotelList= hotellist_inner.select("> div");
            for(Element e:hotelList){
                String imageLink = null, name = null, address = null, rate = null, price = null,rateStar = null,webLink = null;
                Element eImageLink=e.selectFirst("img.hotel_image");
                if(eImageLink!=null)imageLink= eImageLink.attr("src");
                Element eName=e.selectFirst("span.sr-hotel__name\n");
                if(eName!=null)     name= eName.text();
                Element eRate=e.selectFirst("div.bui-review-score__title");
                if(eRate!=null)     rate= eRate.text();
                Element ePrice=e.selectFirst("div.bui-price-display__value");
                if(ePrice!=null)    price= ePrice.text();
                else continue;
                Element eRateStar=e.selectFirst("div.bui-review-score__badge");
                if(eRateStar!=null) rateStar=eRateStar.text();
                Element eAddress=e.selectFirst("div.sr_card_address_line");
                if(eAddress!=null)  address=eAddress.text().replace("Xem trên bản đồ","");
                Element eLinkWeb= e.selectFirst("> div >a");
                if(eLinkWeb!=null)  webLink="https://www.booking.com"+eLinkWeb.attr("href");
                residenceList.add(new Residence(name,"Khách sạn",address,price,rate, rateStar,imageLink, webLink));
            }
        }
        return residenceList;
    }
    protected List<String> getListImage(){
        List<String> imageList= new ArrayList<>();
        Document document= Jsoup.parse(result);
        if(document!=null){
            Element display1= document.selectFirst("div#blockdisplay1");
            if(display1!=null){
                Element gallery_side_reviews_wrapper= display1.selectFirst("> div> div");
                if(display1!=null){
                    Element photo_gird= gallery_side_reviews_wrapper.selectFirst("> div");
                    Elements list= photo_gird.select("> div");
                    for(Element e:list){
                        Element eImage= e.selectFirst("> a");
                        if(eImage!=null){
                            imageList.add(eImage.attr("href"));
                        }
                        else {
                            Elements eImage2= e.select("div.bh-photo-grid-thumb-cell");
                            for(Element element:eImage2){
                                imageList.add(element.selectFirst("> a").attr("href"));
                            }
                        }
                    }
                }
            }
        }
        return imageList;
    }

    public String getResult() {
        return result;
    }
}
