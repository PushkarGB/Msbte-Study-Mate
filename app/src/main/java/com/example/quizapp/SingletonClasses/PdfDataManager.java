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


        pdfFIleMap.put("osy", osy);
        pdfFIleMap.put("css", css);
        pdfFIleMap.put("ste", ste);
        pdfFIleMap.put("ajp", ajp);
        pdfFIleMap.put("est", est);
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