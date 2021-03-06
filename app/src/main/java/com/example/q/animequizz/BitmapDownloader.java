package com.example.q.animequizz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/*To load an image asynchronously into an answer activity*/
public class BitmapDownloader extends AsyncTask<String, String, Bitmap> {



    SoloAnswerActivity act;
    DuoAnswerActivity actDuo;

    //Mode: 0 for solo; 1 for duo
    int mode=0;
    //Mediatype:1:Anime ; 2:Manga ; 3:Character
    int mediatype=-1;
    //MyAnimeList id
    int malid=-1;

    public BitmapDownloader(SoloAnswerActivity act, int type, int malid)
    {
        this.act=act;
        mode=0;
        mediatype=type;
        this.malid=malid;
    }

    public BitmapDownloader(DuoAnswerActivity act, int type, int malid)
    {
        this.actDuo=act;
        mode=1;
        mediatype=type;
        this.malid=malid;
    }


    public BitmapDownloader(SoloAnswerActivity act)
    {
        this.act=act;
        mode=0;
    }

    public BitmapDownloader(DuoAnswerActivity act)
    {
        this.actDuo=act;
        mode=1;
    }

    //Downloads the image asynchronously
    protected Bitmap doInBackground(String... strings)
    {
        URL url=null;
        HttpURLConnection conn=null;
        Bitmap bm =null;

        try
        {
            publishProgress("Trying to connect");
            String urlmedia = strings[0];
            url=new URL(urlmedia);

            Log.i("Anime", "Pomme1: "+urlmedia);
            conn = (HttpURLConnection)url.openConnection();
            Log.i("Anime", "Pomme1: Connexion opened"+urlmedia);

            InputStream stream = null;
            try
            {
                InputStream is = conn.getInputStream();

                stream = new BufferedInputStream(is);
            }
            catch (Exception e)
            {
                Log.i("Anime", "Pomme1: Error when getting stream "+ e.toString());
            }

            publishProgress("Trying decode stream");
            Log.i("Anime", "Pomme1: Trying to decode stream "+urlmedia);
            bm=BitmapFactory.decodeStream(stream);

            stream.close();
            Log.i("Anime", "Pomme1: Decoded stream "+urlmedia);
            publishProgress("Decoded stream");

        }
        catch(Exception e)
        {
            publishProgress(e.toString());
            Log.i("Anime", "Pomme1: when dling bitmap "+e.toString());
        }

        return bm;
    }


    @Override
    protected void onProgressUpdate(String... strings) {
        super.onProgressUpdate(strings);
        for(String i:strings)
        {
            Log.i("AnimeQuizz", "AnimeStuff: " +i);
        }
    }


    //Loads the image in the appropriate acrivity
    @Override
    protected void onPostExecute(Bitmap bitmap) {


        super.onPostExecute(bitmap);

        if(mediatype>=0 && malid>=0)
        {
            if(mode==0)
            {
                act.LoadImage(bitmap, mediatype,malid);
            }
            else if(mode==1)
            {
                actDuo.LoadImage(bitmap, mediatype,malid);
            }
        }
        else
        {
            if(mode==0)
            {
                act.LoadImage(bitmap);
            }
            else if(mode==1)
            {
                actDuo.LoadImage(bitmap);
            }
        }

    }

}
