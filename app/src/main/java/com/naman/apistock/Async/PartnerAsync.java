package com.naman.apistock.Async;

import android.os.AsyncTask;

import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Partner;
import com.naman.apistock.Utils.StringUtils;

import java.util.List;

public class PartnerAsync extends AsyncTask<Void, Void, List> {

    private DatabaseAdapter db;
    private Partner partner;
    private String process;

    public PartnerAsync(DatabaseAdapter db, Partner partner, String process){
        this.db = db;
        this.partner = partner;
        this.process = process;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Void... voids) {
        if(process.equalsIgnoreCase(StringUtils.INSERT_DATA)){
            db.partnerDao().insertPartner(partner);
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.UPDATE_DATA)){
            db.partnerDao().updatePartner(partner);
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.DELETE_DATA)){
            db.partnerDao().deletePartner(partner.getId());
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
            return db.partnerDao().fetchPartnerByType(partner.getType());
        }
        else if(process.equalsIgnoreCase(StringUtils.FETCH_NAME)){
            return db.partnerDao().fetchPartnerNameList(partner.getType());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
    }
}
