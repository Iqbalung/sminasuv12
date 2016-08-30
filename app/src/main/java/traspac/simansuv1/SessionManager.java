package traspac.simansuv1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by UI-21 on 12/08/2016.
 */
public class SessionManager
{
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context mContext;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAMWE = "Login";
    private static final String IS_LOGIN = "IsLogin";

    public SessionManager(Context context)
    {
        this.mContext = context;
        pref = context.getSharedPreferences(PREF_NAMWE,PRIVATE_MODE);
        editor = pref.edit();
    }

    public HashMap<String,String> getDataUser()
    {
        HashMap<String,String> login = new HashMap<String, String>();
        login.put(Config.TAG_ID_USER,pref.getString(Config.TAG_ID_USER,null));
        login.put(Config.TAG_USERNAME,pref.getString(Config.TAG_USERNAME,null));
        return login;
    }

    public void createSessionLogin(String id,String username)
    {
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(Config.TAG_ID_USER,id);
        editor.putString(Config.TAG_USERNAME,username);
        editor.commit();
    }

    public void clearSession()
    {
        editor.clear();
        editor.commit();
    }


    public void cekLogin()
    {
        if (this.isLogin()){
            Intent intent = new Intent(mContext,MainMenu.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }

    public boolean isLogin() {

        return pref.getBoolean(IS_LOGIN,false);
    }
}
