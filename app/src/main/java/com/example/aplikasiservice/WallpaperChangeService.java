package com.example.aplikasiservice;

import android.app.Service;
import android.app.WallpaperManager;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.os.IBinder;
import java.io.IOException;

public class WallpaperChangeService extends Service implements Runnable {
    /*Referensi gambar wallpaper yang disimpan dalam sebuah array, wallpaper1 dan wallpaper2 adalah nama file png dan jpeg anda*/
    private int wallpaperId[] = {R.drawable.wallpaper1, R.drawable.wallpaper2};
    /*deklarasi variabel yang di gunakan untuk menyimpan durasi yang di pilih user*/
    private int waktu;
    /*deklarasi variabel flag untuk nge check gambar mana yang akan di tempilkan*/
    private int FLAG=0;
    private Thread t;
    /*Deklarasi 2 buah variabel bitmap untuk menyimpan gambar wallpaper */
    private Bitmap gambar;
    private Bitmap gambar1;

    @Override
    public IBinder onBind(Intent arg0) {
        //TODO Auto-generate method stub
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flag, int startId)
    {
        super.onStartCommand(intent,flag, startId);
        /*peroleh bundle yang dikirim melalui intent dari MainActivity*/
        Bundle bundle=intent.getExtras();
        /* baca nilai yang tersimpan dengan kunci 'durasi' */
        waktu = bundle.getInt("durasi");
        /* inisialisasi object bitmap dengan gambar wallpaper */
        gambar=BitmapFactory.decodeResource(getResources(),wallpaperId[0]);
        gambar1=BitmapFactory.decodeResource(getResources(),wallpaperId[1]);
        /* memulai sebuah thread agar service tetap berjalan di latar belakang */

        t = new Thread(WallpaperChangeService.this);
        t.start();
        return START_STICKY_COMPATIBILITY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
    /*method run() yanh berisi kode yang di eksekusi oleh thread yang baru saja di buat*/
    @Override
    public void run(){
        //TODO Auto-generate method stub
        WallpaperManager myWallpaper;
        myWallpaper = WallpaperManager.getInstance(this);
        try{
            while (true){
                if(FLAG==0){
                    myWallpaper.setBitmap(gambar);
                    FLAG++;
                }
                else {
                    myWallpaper.setBitmap(gambar1);
                    FLAG--;
                }
                Thread.sleep(100*waktu);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
