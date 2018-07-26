package com.example.gagan.hrconsole.helper;

import com.example.gagan.hrconsole.models.BasicListModel;
import com.example.gagan.hrconsole.models.Datas;
import com.example.gagan.hrconsole.models.DividerModel;
import com.example.gagan.hrconsole.models.HeadingModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gagan on 6/18/2018.
 */

public class MainHelper {
    public final List<BasicListModel> items = new ArrayList<>();
    public final List<Datas> mainDatas = new ArrayList<>();
    public final List<String> spinnerItems = new ArrayList<>();
    private final Datas basic = new Datas("Basic+Da", 0);
    private final Datas hra = new Datas("HRA", 0);
    private final Datas conveyance = new Datas("Conveyance", 0);
    private final Datas medical = new Datas("Medical", 0);
    private final Datas special = new Datas("Special", 0);
    private final Datas gross = new Datas("Gross", 0);

    private final DividerModel dividerModel = new DividerModel();

    private final HeadingModel empoyerContributionHeader = new HeadingModel("Employer Contribution");
    private final Datas pf = new Datas("PF", 0);
    private final Datas esi = new Datas("ESI", 0);
    private final Datas insurance = new Datas("Insurence", 0);
    private final Datas empoyerContributionTotal = new Datas("Total", 0);

    //Divider
    private final HeadingModel empoyeeContributionHeader = new HeadingModel("Employee Deduction");
    private final Datas ecPF = new Datas("PF", 0);
    private final Datas ecEsi = new Datas("ESI", 0);
    private final Datas ecPt = new Datas("PT", 0);
    private final Datas employeeDeductionTotal = new Datas("Total", 0);

    private final HeadingModel empoyeenetPay = new HeadingModel("Net pay");
    private final Datas netpay = new Datas("Payable", 0);


    private final Datas hs = new Datas("Highly Skilled", 16106);
    private final Datas skilled = new Datas("Skilled", 14704);
    private final Datas ss = new Datas("Semi Skilled", 13429);
    private final Datas us = new Datas("Unskilled", 12270);
    private final Datas managerial = new Datas("Managerial", 17105);
    private final Datas as = new Datas("Assistant Managerial", 16106);
    private final Datas ae = new Datas("Admin Executive", 14704);
    private final Datas clerk = new Datas("Clerk", 13429);
    private final Datas driver = new Datas("Driver", 13429);
    private final Datas attender = new Datas("Attender", 12271);
    private int mW;
    private int ctc;


    public MainHelper() {
        items.add(basic);
        items.add(hra);
        items.add(conveyance);
        items.add(medical);
        items.add(special);
        items.add(gross);

        items.add(dividerModel);
        items.add(empoyerContributionHeader);
        items.add(dividerModel);
        items.add(pf);
        items.add(esi);
        items.add(insurance);
        items.add(empoyerContributionTotal);

        items.add(dividerModel);
        items.add(empoyeeContributionHeader);
        items.add(dividerModel);
        items.add(ecPF);
        items.add(ecEsi);
        items.add(ecPt);
        items.add(employeeDeductionTotal);

        items.add(dividerModel);
        items.add(empoyeenetPay);
        items.add(dividerModel);
        items.add(netpay);
        initSpinner();
    }

    private void initSpinner() {
        mainDatas.add(new Datas("None", 0));
        mainDatas.add(hs);
        mainDatas.add(skilled);
        mainDatas.add(ss);
        mainDatas.add(us);
        mainDatas.add(managerial);
        mainDatas.add(as);
        mainDatas.add(ae);
        mainDatas.add(clerk);
        mainDatas.add(driver);
        mainDatas.add(attender);
        for (Datas item : mainDatas) {
            spinnerItems.add(item.getName());
        }
    }

    public List<BasicListModel> getItems() {
        return items;
    }

    public boolean putCtc(int ctc) {
        this.ctc = ctc;
        if (mW > 0) {
            int ba = max(ctc / 2, mW);
            basic.setAmount(ba);
            gross.setAmount(calculateGross(basic.getAmount(), true));


            int ctcpredicted = predictCtc(ba, gross.getAmount());

            if (ctcpredicted <= ctc) {
                int specialAllowence = ctc - ctcpredicted;
                special.setAmount(specialAllowence);
                gross.setAmount(gross.getAmount() + special.getAmount());
            } else {
                gross.setAmount(calculateGross(basic.getAmount(), false));

                ctcpredicted = predictCtc(ba, gross.getAmount());
            }
//=IF(G5>15000,1800,ROUND(12%*G5,0))
            ecPF.setAmount(basic.getAmount() > 15000 ? 1800 : 12 * basic.getAmount() / 100);
//=IF(G10>21000,0,ROUND(1.75%*G10,0))
            ecEsi.setAmount(gross.getAmount() > 21000 ?
                    0 : (int) (1.75 * gross.getAmount() / 100));

// =IF(G10>14999,200,0)
            ecPt.setAmount(gross.getAmount() > 14999 ? 200 : 0);


            empoyerContributionTotal.setAmount(pf.getAmount() + esi.getAmount() + insurance.getAmount());
            employeeDeductionTotal.setAmount(ecPF.getAmount() + ecPt.getAmount() + ecEsi.getAmount());

            netpay.setAmount(gross.getAmount() - employeeDeductionTotal.getAmount());
            return true;
        }
        return false;

    }

    public int predictCtc(int _ba, int _gross) {

        int employerContribution = 0, pF = 0, ESI = 0, insurance = 0;
        if (_ba >= 15000)
            pF = 1800;
        else
            pF = (12 * _ba) / 100;

        if (!(_gross > 21000))
            ESI = (int) (4.75 * _gross) / 100;

        if (ESI == 0) {
            insurance = getInsurence(ctc * 12);
        }
        employerContribution = pF + ESI + insurance;
        this.insurance.setAmount(insurance);
        pf.setAmount(pF);
        esi.setAmount(ESI);
        this.empoyerContributionTotal.setAmount(employerContribution);

        return employerContribution + _gross;
    }

    private int getInsurence(int ctc) {
//        100000	500
        if (between(ctc, 0, 100000))
            return 500;
//        150000	750
        if (between(ctc, 10000, 200000))
            return 1000;
        //        200000	1000

        if (between(ctc, 200000, 250000))
            return 1500;
        //        250000	1500

        if (between(ctc, 250000, 300000))
            return 2000;
        //        300000	2000

        if (between(ctc, 250000, 350000))
            return 2500;
        //        350000	2500

        if (between(ctc, 350000, 400000))
            return 3000;
        //        400000	3000

        if (between(ctc, 400000, 450000))
            return 3500;
        //        450000	3500

        if (between(ctc, 450000, 500000))
            return 4000;
        //        500000	4000

        if (between(ctc, 500000, 600000))
            return 4000;
        //        600000	4000

        if (between(ctc, 600000, 700000))
            return 4500;
        //        700000	4500

        if (between(ctc, 700000, 1000000))
            return 5000;
//        1000000	5000

        return 0;
    }

    public boolean between(int i, int minValueInclusive, int maxValueInclusive) {
        if (i >= minValueInclusive && i <= maxValueInclusive)
            return true;
        else
            return false;
    }

    public int calculateGross(int _b, boolean _takeExtras) {
        hra.setAmount((40 * _b / 100));
        int ret = _b + hra.getAmount();
        if (_takeExtras) {
            special.setAmount(0);
            conveyance.setAmount(1600);
            medical.setAmount(1250);
        } else {
            conveyance.setAmount(0);
            medical.setAmount(0);
        }
        ret = ret + conveyance.getAmount() + medical.getAmount() + special.getAmount();
        return ret;
    }

    public int max(int a, int b) {
        return a > b ? a : b;
    }

    public List<String> getSpinnerItems() {
        return spinnerItems;
    }

    public List<Datas> getSpinnerDatas() {
        return mainDatas;
    }

    public int getmW() {
        return mW;
    }

    public void setSpinnerItemSelected(int position) {
        if (position == 0) {
            mW = -1;
            reset();
            return;
        }
        mW = mainDatas.get(position).getAmount();
        if (ctc != 0) {
            putCtc(ctc);
        }
    }

    public void reset() {
        for (BasicListModel item : getItems()) {
            if (item instanceof Datas) {
                ((Datas) item).setAmount(0);
            }
        }
    }
}

