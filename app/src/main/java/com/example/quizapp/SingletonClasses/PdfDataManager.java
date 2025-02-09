package com.example.quizapp.SingletonClasses;

import java.util.HashMap;

public class PdfDataManager {
    private static PdfDataManager instance;
    private HashMap<String, HashMap<String, String>> pdfFIleMap;

    /*
     * URL Formay : https://drive.google.com/file/d/1V_I9yNHh7Qo-F0aMapkw2Sqg0v4KeFAw/view?usp=drive_link"
     * here , https://drive.google.com + (/file  OR  /folder) + (uniqueID) + (/view OR "") + ?usp=drive_link
     * 1st Logic  : store only FileID :  "(/file  OR  /folder) + (uniqueID) + (/view OR "")" amd add additional code like a function which will perform concatenation and generate URLs .
     * In this way we will have a edge when our file's location has changed from googleDrive to other location or just another file in googleDrive then modifying it will be little bit easier
     * 2nd Logic : What if we directly store the entire URL in the Map , but this way it has to be considered that our links will not change in future because then it will be very hectic to change each and every url
     * But also in current scenario we are not going to change the location of our pdfs so it we will go for 2nd Logic
     * */

    private PdfDataManager() {
        pdfFIleMap = new HashMap<>();

        HashMap<String, String> osy = new HashMap<>();
        osy.put("unit1", "https://drive.google.com/file/d/1V_I9yNHh7Qo-F0aMapkw2Sqg0v4KeFAw/view?usp=drive_link");
        osy.put("unit2", "https://drive.google.com/file/d/1Y0or-1Xzqc9W-KN11R5l7cjz_f-9rjmY/view?usp=drive_link");
        osy.put("unit3", "https://drive.google.com/file/d/1s8dKaWhVIY5SE1-5PCPIcRuaV8DjFj4S/view?usp=drive_link");
        osy.put("syllabus", "https://drive.google.com/drive/folders/17k4f2YN72R1U8KzK6rIGjhIAjC1yE5C0?usp=drive_link");
        osy.put("papers", "https://drive.google.com/drive/folders/1mxQW6xJuklSc3xpNpDhUZbHRDUDkYt5I?usp=drive_link");
        osy.put("notes", "https://drive.google.com/drive/folders/1ryfrkHNQMm6WQx_VobA7A09V7CTe2jJW?usp=drive_link");

        HashMap<String, String> css = new HashMap<>();
        css.put("unit1", "https://drive.google.com/file/d/1iLB9X1dggCd5VDKz3yUUlK7V984EHIcx/view?usp=drive_link");
        css.put("syllabus", "https://drive.google.com/drive/folders/1n6opwRCNmDvL2hoNsIeVbvjYi0qQs1SQ?usp=drive_link");
        css.put("papers", "https://drive.google.com/drive/folders/1HDN7Wr56fjCVfJ3xUftu2yfsyhxH163L?usp=drive_link");
        css.put("notes", "https://drive.google.com/drive/folders/1vmTc5TIAE5wcHyDNI4-0uGPvzqqK2Tlj?usp=drive_link");

        HashMap<String, String> ste = new HashMap<>();
        ste.put("syllabus", "https://drive.google.com/drive/folders/19bWbpx8oDq5vC6EO48FcxjJtSfryG-y7?usp=drive_link");
        ste.put("papers", "https://drive.google.com/drive/folders/1zCMFEvuEDfuI3A4A8FlN-Fjs0iOxMV2l?usp=drive_link");
        ste.put("notes", "https://drive.google.com/drive/folders/1uldyOXI-tiOt6-bL0dwByfKLx2gHafnb?usp=drive_link");
        ste.put("unit1", "https://www.notion.so/3137a4ca9348498fa88751507c7ee88b?v=21f38b90f7c74fe28b1776d0dc8705b7&pvs=4");

        HashMap<String, String> ajp = new HashMap<>();
        ajp.put("syllabus", "https://drive.google.com/file/d/1UKRs1HRPabLgsH1zuIj2DQM-jrBQadLq/view?usp=drive_link");
        ajp.put("notes", "https://drive.google.com/drive/folders/1tFAoUvWfheAbjNJj-3QKWIhfKsgf_bQX?usp=drive_link");
        ajp.put("mcq", "https://drive.google.com/drive/folders/1hkGQJ_8F6u14q0PbEqNRMdPQeTgGa9_n?usp=drive_link");

        HashMap<String, String> est = new HashMap<>();
        est.put("syllabus", "https://drive.google.com/file/d/1UkdsucdXEcinYmRXmgPjTaSnlcdNrxCh/view?usp=drive_link");
        est.put("mcq", "https://drive.google.com/drive/folders/1C1t4XeaRoKxcD6oHRbSfwn8eec4RGoKC?usp=drive_link");

        HashMap<String, String> mad = new HashMap<>();
        mad.put("syllabus", "https://drive.google.com/drive/folders/19bWbpx8oDq5vC6EO48FcxjJtSfryG-y7?usp=drive_link");
        mad.put("papers", "https://drive.google.com/drive/folders/10Jgz0syyLXZzGw9pm4ssko7Is68JkKqS?usp=drive_link");
        mad.put("notes", "https://drive.google.com/drive/folders/1vk5Qmao3AmrRLcftX6SpFC5YSGIKFyDC?usp=drive_link");
        mad.put("unit1", "https://docs.google.com/document/d/1SrleQBaFowFukO7fi4jPp2SblaNInJbo/edit?usp=drive_link&ouid=118028319398943327573&rtpof=true&sd=true");
        mad.put("unit2", "https://docs.google.com/document/d/1hpswk-NNTeEsWnlHzvLP__hQ7ynFWkXL/edit?usp=drive_link&ouid=118028319398943327573&rtpof=true&sd=true");
        mad.put("unit3", "https://docs.google.com/document/d/1weZPVT14g9kpRWyc-cN8Y-AYExibM1-G/edit?usp=drive_link&ouid=118028319398943327573&rtpof=true&sd=true");
        mad.put("unit4", "https://docs.google.com/document/d/1c8NQoSrQ1cH76Rtrl3mW8MtBuujI8den/edit?usp=drive_link&ouid=118028319398943327573&rtpof=true&sd=true");
        mad.put("unit5", "https://docs.google.com/document/d/1nvkZXrHjwe2ipWKXNuz6xCp77v9VaIe_/edit?usp=drive_link&ouid=118028319398943327573&rtpof=true&sd=true");
        
        HashMap<String, String> pwp = new HashMap<>();
        pwp.put("syllabus", "https://drive.google.com/drive/folders/1lfZobhsfhY7LEiQXwOh5FXObD-J0sE1c?usp=drive_link");
        pwp.put("papers", "https://drive.google.com/drive/folders/1KPU-nIUF4esJdtP76kKKc-3bk1HOEhkG?usp=drive_link");
        pwp.put("notes", "https://drive.google.com/drive/folders/1lgXZy8FRLIuIh0mvldU4B6a4ZSxaTCtL?usp=drive_link");
        pwp.put("unit1", "https://drive.google.com/drive/folders/1FjQJcg8_jiNXurxQtGyh-lG0-EdCbcao?usp=drive_link");
        pwp.put("unit2", "https://drive.google.com/drive/folders/12s6-kvgcAaZxZEkgJ8dpA-No3ZJBM9tL?usp=drive_link");
        pwp.put("unit3", "https://drive.google.com/drive/folders/12l6cPHFR_ToHfzu5QR934ZRDSXGvcQQD?usp=drive_link");
        pwp.put("unit4", "https://drive.google.com/drive/folders/1oAm2rk7GoNKvdNQDl_8D7v_z-1vZEGAF?usp=drive_link");
        pwp.put("unit5", "https://drive.google.com/drive/folders/1oIGYFLiNOaIpy80xDSL5NpzkUWu6wJvK?usp=drive_link");
        pwp.put("unit6", "https://drive.google.com/drive/folders/1oPesOEciGQCBKKEpSjS1phqKM-AQi6Uv?usp=drive_link");
        
        HashMap<String, String> wbp = new HashMap<>();
        wbp.put("syllabus", "https://drive.google.com/drive/folders/1mCBtKeJWrBS3BKwiQDsit3HDn1yuATo1?usp=drive_link");
        wbp.put("papers", "https://drive.google.com/drive/folders/1m6ln6eGQTFAHT2XbJ33WQ81FbEwzYizy?usp=drive_link");
        wbp.put("notes", "https://drive.google.com/drive/folders/1m9ND6_Ag2XdwTf0L10JUUDRegzVUA8RI?usp=drive_link");
        wbp.put("unit1", "https://drive.google.com/drive/folders/1nOzj3_y1man9Lg0FU93bqhCvLqThju5e?usp=drive_link");
        wbp.put("unit2", "https://drive.google.com/drive/folders/1nXPoW2aZrXBP9OCbuPCj1U2ucZ3qJys3?usp=drive_link");
        wbp.put("unit3", "https://drive.google.com/drive/folders/1nZisC_Ta3jdJhNdgmRlps_JdrEEgiOID?usp=drive_link");
        wbp.put("unit4", "https://drive.google.com/drive/folders/1nil4_Cn1j7nmcqSkWBG3raoTXlA3B1O8?usp=drive_link");
        wbp.put("unit5", "https://drive.google.com/drive/folders/1o1SB1aDTai4HlO_3d3csM0nW82mQacLD?usp=drive_link");
        
        HashMap<String, String> nis = new HashMap<>();
        nis.put("syllabus", "https://drive.google.com/drive/folders/1mbGvHZd9I5KjneTtkialn9uIaA6YgQVf?usp=drive_link");
        nis.put("papers", "https://drive.google.com/drive/folders/1mPLxuAd274dbRdpwBXHpwHKBuHpGTYTN?usp=drive_link");
        nis.put("notes", "https://drive.google.com/drive/folders/1mROOY642ItkadTVJzKX051kCKM0_OEg8?usp=drive_link");
        nis.put("unit1", "https://drive.google.com/drive/folders/1oWgdgRDe5v28q_1GlbMs75SLWGNpcOpC?usp=drive_link");
        nis.put("unit2", "https://drive.google.com/drive/folders/1odPzS0mxoiofBeUjbacsmC6guuT22P35?usp=drive_link");
        nis.put("unit3", "https://drive.google.com/drive/folders/1oh_YEaBREWSAVQrwSyLpr17R9_-5fdxZ?usp=drive_link");
        nis.put("unit4", "https://drive.google.com/drive/folders/1ohi-SkochkwW-JKs1WjWPgiL-voWI64d?usp=drive_link");
        nis.put("unit5", "https://drive.google.com/drive/folders/1olSo_dJVdPHS39b8WSlfH_OUdlaf3Zii?usp=drive_link");

        HashMap<String, String> eti = new HashMap<>();
        eti.put("syllabus", "https://drive.google.com/drive/folders/1nHSDmvi00FD9KNeI51F9EI9cRXqMLb3I?usp=drive_link");
        eti.put("notes", "https://drive.google.com/drive/folders/1mhcfd-IjNDN347GoCIMpleinUYLf6UVT?usp=drive_link");
        eti.put("mcq", "https://drive.google.com/drive/folders/1W5WLhqOQ0fX1nQG5DFmSDawz71jcPKN9?usp=drive_link");

        HashMap<String, String> mgt = new HashMap<>();
        mgt.put("syllabus", "https://drive.google.com/drive/folders/1lyHngHrhyZCBotWLx9N-XwLfSUwJb_f0?usp=drive_link");
        mgt.put("notes", "https://drive.google.com/drive/folders/1m5w-cR03VDI3utSNVzAKMDxLALyo0qaI?usp=drive_link");
        mgt.put("mcq", "https://drive.google.com/drive/folders/1lgsIHPxX3j3Uud9xx4ryE1umcre_ujTM?usp=drive_link");
        
        HashMap<String, String> ede = new HashMap<>();
        ede.put("syllabus", "https://drive.google.com/drive/folders/1nNu61apIVqoSicVX_xNVSZvw5JocqtZ1?usp=drive_link");
        ede.put("notes", "https://drive.google.com/drive/folders/1nOtpiVVfCmW2kBIOZGDEi7KOWFm4REXj?usp=drive_link");

        pdfFIleMap.put("osy", osy);
        pdfFIleMap.put("css", css);
        pdfFIleMap.put("ste", ste);
        pdfFIleMap.put("ajp", ajp);
        pdfFIleMap.put("est", est);
        pdfFIleMap.put("mad", mad);
        pdfFIleMap.put("pwp", pwp);
        pdfFIleMap.put("wbp", wbp);
        pdfFIleMap.put("ede", ede);
        pdfFIleMap.put("eti", eti);
        pdfFIleMap.put("mgt", mgt);

    }

    public static synchronized PdfDataManager getInstance() {
        if (instance == null) {
            instance = new PdfDataManager();
        }
        return instance;
    }

    public String getPdfUrl(String courseKey, String unitKey) {
        HashMap<String, String> nestedMap = pdfFIleMap.get(courseKey);
        if (nestedMap != null) {
            String PdfLink = nestedMap.get(unitKey);
            if (PdfLink != null) {
                return PdfLink;
            } else {
                return "https://drive.google.com/file/d/1lEq5jj3uZmEtby6bKU5TYJwr3vp2Fl6v/view?usp=drive_link";
            }
        } else {
            return "https://drive.google.com/file/d/1HxQkWUQSvR8gWG8mf91pDsulMzhT4WYo/view?usp=drive_link";
        }
    }
}