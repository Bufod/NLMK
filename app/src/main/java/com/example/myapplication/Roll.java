package com.example.myapplication;
import java.util.Date;
public class Roll {
    public int attr_id;
    public String date_beg_born;
    public String e_born_unit;
    public int e_born_unit_code;
    public int id;
    public int m_unit_id;
    public double offsets;
    public String title;
    public double vavg;

    public Roll(String attr_id, String date_beg_born, String e_born_unit, String e_born_unit_code,
                String id, String m_unit_id, String offsets, String title, String vavg) {
        this.attr_id = Integer.parseInt(attr_id);
        this.date_beg_born = date_beg_born;
        this.e_born_unit = e_born_unit;
        this.e_born_unit_code = Integer.parseInt(e_born_unit_code);
        this.id = Integer.parseInt(id);
        this.m_unit_id = Integer.parseInt(m_unit_id);
        this.offsets = Double.parseDouble(offsets);
        this.title = title;
        this.vavg = Double.parseDouble(vavg);
    }
}