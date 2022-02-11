package com.example.d26m01y22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.d26m01y22.tabales.Workers;

import java.util.ArrayList;

import static com.example.d26m01y22.tabales.Workers.KEY_ID;
import static com.example.d26m01y22.tabales.Workers.TABLE_WORKERS;

/**
 * @author		Harel Leibovich <hl9163@bs.amalnet.k12.il>
 * @version	2.0
 * @since		27/01/2022
 * add or update worker details
 */
public class activity_input_worker extends AppCompatActivity {
    LinearLayout workModeLL, firstNameLL, lastNameLL, compenyLL, phoneLL;
    TextView titlePage;
    Button saveAndContinue;
    Switch workMode;
    EditText pId,cId, firstNameField,lastNameField,comp,phone_numberField;

    AlertDialog.Builder adb;

    SQLiteDatabase db;
    HelperDB hlp;
    Cursor crsr;
    ArrayList<String> personal_id_tb = new ArrayList<>();
    ArrayList<String> card_id_tb = new ArrayList<>();
    String[] columns = {"PERSONAL_ID","CARD_ID"};
    String selectionId = Workers.PERSONAL_ID+"=?";

    int key;

    static int mode;
    static boolean fistStep = true;
    static String personal_id,card_id,first_name,last_name, worker_company,phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_worker);

        fistStep = true;
        hlp = new HelperDB(this);
        db = hlp.getWritableDatabase();
        db.close();

        workModeLL = (LinearLayout) findViewById(R.id.workingLL);
        firstNameLL = (LinearLayout) findViewById(R.id.firstNameLL);
        lastNameLL = (LinearLayout) findViewById(R.id.lastNameLL);
        compenyLL = (LinearLayout) findViewById(R.id.compenyLL);
        phoneLL = (LinearLayout) findViewById(R.id.phoneLL);

        titlePage = (TextView) findViewById(R.id.titleUpdateWorkers);
        saveAndContinue = (Button) findViewById(R.id.saveAndContinueButton);
        workMode = (Switch) findViewById(R.id.switch1);

        pId = (EditText) findViewById(R.id.Personal_id);
        cId = (EditText) findViewById(R.id.card_id);
        firstNameField = (EditText) findViewById(R.id.first_name);
        lastNameField = (EditText) findViewById(R.id.last_name);
        comp = (EditText) findViewById(R.id.compeny);
        phone_numberField = (EditText) findViewById(R.id.Phone);

        Intent gi = getIntent();
        mode = gi.getIntExtra("mode",-1);
        workModeLL.setVisibility(View.INVISIBLE);
        if (mode == 0){
            titlePage.setText("add a new worker:");
        }else if (mode == 1){
            titlePage.setText("edit worker details:");
            firstNameLL.setVisibility(View.INVISIBLE);
            lastNameLL.setVisibility(View.INVISIBLE);
            compenyLL.setVisibility(View.INVISIBLE);
            phoneLL.setVisibility(View.INVISIBLE);
            saveAndContinue.setText("next");
        }
    }


    /**
     * check if the personal id and card id are exist in the database
     * <p>
     *
     * @param	personalId Description	String personal Id
     * @param cardId Description String card Id
     */
    public int isAlreadyExist(String personalId, String cardId){
        int good = 0;
        personal_id_tb = new ArrayList<>();
        card_id_tb = new ArrayList<>();
        db = hlp.getWritableDatabase();
        crsr = db.query(TABLE_WORKERS, columns, null, null, null, null, null);
        int col2 = crsr.getColumnIndex(Workers.CARD_ID);
        int col1 = crsr.getColumnIndex(Workers.PERSONAL_ID);
        crsr.moveToFirst();
        while (!crsr.isAfterLast()) {
            String id = crsr.getString(col1);
            String cId = crsr.getString(col2);
            personal_id_tb.add(id);
            card_id_tb.add(cId);
            crsr.moveToNext();
        }
        crsr.close();
        db.close();
        for (int i = 0;i<personal_id_tb.size();i++){
            if (personalId.equals(personal_id_tb.get(i))){
                good++;
            }
        }
        for (int i = 0;i<card_id_tb.size();i++){
            if (cardId.equals(card_id_tb.get(i))){
                good++;
            }
        }
        return good;
    }
    /**
     * read a line from the database by existing personal id
     * <p>
     *
     * @param	id Description	String personal Id
     * @return  String[]result Description include all the details  [KEY_ID, PERSONAL_ID, CARD_ID, FIRST_NAME, LAST_NAME, WORKER_COMPENY, PHONE_NUMBER, IS_WORKING]
     */
    public String[] readById(String id){
        String[]selectionArg = {id};
        String[]result = new String[8];
        db=hlp.getReadableDatabase();
        crsr = db.query(TABLE_WORKERS, null, selectionId, selectionArg, null, null, null);
        int col = crsr.getColumnIndex(KEY_ID);
        int col1 = crsr.getColumnIndex(Workers.PERSONAL_ID);
        int col2 = crsr.getColumnIndex(Workers.CARD_ID);
        int col3 = crsr.getColumnIndex(Workers.NAME);
        int col4 = crsr.getColumnIndex(Workers.LAST_NAME);
        int col5 = crsr.getColumnIndex(Workers.WORKER_COMPANY);
        int col6 = crsr.getColumnIndex(Workers.PHONE_NUMBER);
        int col7 = crsr.getColumnIndex(Workers.IS_WORKING);
        crsr.moveToFirst();
        result[0] = String.valueOf(crsr.getInt(col));
        result[1] = crsr.getString(col1);
        result[2] = crsr.getString(col2);
        result[3] = crsr.getString(col3);
        result[4] = crsr.getString(col4);
        result[5] = crsr.getString(col5);
        result[6] = crsr.getString(col6);
        result[7] = String.valueOf(crsr.getInt(col7));
        crsr.close();
        db.close();
        return result;
    }
    /**
     * save the data of a new worker on the database
     * <p>
     *
     */
    public void save_data(){
        personal_id = pId.getText().toString();
        card_id = cId.getText().toString();
        first_name = firstNameField.getText().toString();
        last_name = lastNameField.getText().toString();
        worker_company = comp.getText().toString();
        phone_number = phone_numberField.getText().toString();
        if (check_inputs(personal_id, card_id, first_name, last_name,worker_company)){
            if (mode == 0){
                ContentValues cv = new ContentValues();
                cv.put(Workers.CARD_ID,card_id);
                cv.put(Workers.LAST_NAME,last_name);
                cv.put(Workers.NAME,first_name);
                cv.put(Workers.WORKER_COMPANY,worker_company);
                cv.put(Workers.PERSONAL_ID,personal_id);
                cv.put(Workers.PHONE_NUMBER,phone_number);
                cv.put(Workers.IS_WORKING,1);
                db = hlp.getWritableDatabase();

                db.insert(TABLE_WORKERS, null, cv);

                db.close();
                finish();
            }

        }else {
            popErrorMassage();
        }


    }
    /**
     * check if the program can save data in the database.
     * by checking the length of each parameter is bigger then 0 and the id is possible (by the israeli format)
     * <p>
     *
     * @param	id Description	String personal Id
     * @param	cId Description	String card id
     * @param	fn Description	String fist name
     * @param	ln Description	String last name
     * @param	wc Description	String worker company
     * @return true/false Description false - cannot save, true - can save.
     */
    public boolean check_inputs(String id, String cId, String fn, String ln, String wc){
        if (id.length() == 0 || !check_id(id) || cId.length() == 0 || fn.length() == 0 || ln.length() == 0 || wc.length() == 0 || isAlreadyExist(id,cId) !=0){
            return false;
        }
        return true;
    }
    /**
     * checking the id by the israeli format
     * <p>
     *
     * @param	id_num Description	String personal Id
     * @return  true/false true - ok id, false - not israeli id
     */
    public boolean check_id(String id_num){
        if (id_num.length()<9){
            String zero ="";
            for (int i=0;i>id_num.length()-9;i--){
                zero+="0";
            }
            id_num = zero+id_num;
        }
        int counter = 0;
        int counter2 = 0;
        int num = 1;
        int last_digit;
        for (int i =0;i<id_num.length();i++){
            int current_num =Character.getNumericValue(id_num.charAt(i));
            current_num *= num;
            if (num == 1){
                num = 2;
            }else{
                num = 1;
            }
            if (current_num >9){
                current_num = (current_num/10)+(current_num%10);
            }
            counter+=current_num;
            if (i != id_num.length()-1){
                counter2+=current_num;
            }
        }
        if (10-(counter2%10) != 10){
            last_digit = 10-(counter2%10);
        }else{
            last_digit = 0;
        }

        if (last_digit == (int) Character.getNumericValue(id_num.charAt(id_num.length()-1))){
            if (counter%10 == 0){
                return true;
            }
            return false;
        }else{
            return false;
        }
    }
    /**
     * save all the updated info of a worker by removing there line and rewrite the info
     * <p>
     *
     */
    public void update_worker_details(){
        int isWorking;
        personal_id = pId.getText().toString();
        card_id = cId.getText().toString();
        first_name = firstNameField.getText().toString();
        last_name = lastNameField.getText().toString();
        worker_company = comp.getText().toString();
        phone_number = phone_numberField.getText().toString();
        if (workMode.isChecked()){
            isWorking = 0;
        }else{
            isWorking = 1;
        }
        if ( first_name.length() == 0 || last_name.length() == 0 || worker_company.length() == 0){
            popErrorMassage();
            return;
        }
        db = hlp.getWritableDatabase();
        db.delete(TABLE_WORKERS, KEY_ID+"=?", new String[]{Integer.toString(key)});
        db.close();

        ContentValues cv = new ContentValues();
        cv.put(Workers.CARD_ID,card_id);
        cv.put(Workers.LAST_NAME,last_name);
        cv.put(Workers.NAME,first_name);
        cv.put(Workers.WORKER_COMPANY,worker_company);
        cv.put(Workers.PERSONAL_ID,personal_id);
        cv.put(Workers.PHONE_NUMBER,phone_number);
        cv.put(Workers.IS_WORKING,isWorking);
        db = hlp.getWritableDatabase();

        db.insert(TABLE_WORKERS, null, cv);

        db.close();
        finish();
    }
    /**
     * pop error alert dialog massage to the user
     * <p>
     *
     */
    public void popErrorMassage(){
        adb = new AlertDialog.Builder(this);
        adb.setCancelable(false);
        adb.setTitle("wrong input!");
        adb.setMessage("you entered wrong input to one or more of the input fields");
        adb.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                dialog.dismiss();
            }
        });
        AlertDialog ad = adb.create();
        ad.show();

    }
    /**
     * for each mode it is changing - mode 0 - add worker -
     * on click it will begin the saving posses and jump to save_data() function
     * if the code is run on mode 1 - update worker details - the process dividing to two parts:
     * part one is enter only the card and personal ids. |when I did this part there were 2 challenges:
     * first: how to block those two editTexts (; . the second was: how i check properly the matching?
     * the answer to the first challenge is a easy search in google (; . the answer to the second challenge is
     * I combine two functions- the first is isAlreadyExist that search if the personal id and the card id
     * are existing somewhere in the Workers's database and after reading the line of the personal id
     * the program check if there is a matching in the cards id.|
     * second part is show the user all the current user data in the right place and save when he click save
     *
     * <p>
     *
     * @param	view Description	button
     */
    public void saveWorker(View view) {
        if (mode == 0){
            save_data();
        }else if (mode == 1){
            if (fistStep){
                personal_id = pId.getText().toString();
                card_id = cId.getText().toString();
                if (isAlreadyExist(personal_id,card_id) == 2){
                    String[]details = readById(personal_id);
                    if (details[2].equals(card_id)){
                        firstNameLL.setVisibility(View.VISIBLE);
                        lastNameLL.setVisibility(View.VISIBLE);
                        compenyLL.setVisibility(View.VISIBLE);
                        phoneLL.setVisibility(View.VISIBLE);
                        workModeLL.setVisibility(View.VISIBLE);
                        firstNameField.setText(details[3]);
                        lastNameField.setText(details[4]);
                        comp.setText(details[5]);
                        phone_numberField.setText(details[6]);
                        int wM = Integer.parseInt(details[7]);
                        if (wM == 1){
                            workMode.setChecked(false);
                        }else{
                            workMode.setChecked(true);
                        }
                        pId.setFocusable(false);
                        cId.setFocusable(false);
                        key = Integer.parseInt(details[0]);
                        saveAndContinue.setText("save");
                        fistStep = false;
                    }else{
                        popErrorMassage();
                    }
                }else{
                    popErrorMassage();
                }
            }else{
                update_worker_details();
            }

        }

    }
    /**
     * back to menu
     *
     * <p>
     * @param view button
     */
    public void back_to_main_menu(View view) {
        finish();
    }
    /**
     * create the menu.
     * <p>
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    /**
     * move to the credits activity.
     * <p>
     *
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.return_mainMenu){
            Intent si = new Intent(this,MainActivity.class);
            startActivity(si);
            return true;
        }else if(id == R.id.credits){
            Intent si = new Intent(this,creditsActivity.class);
            startActivity(si);
            return true;

        }
        return true;
    }
}