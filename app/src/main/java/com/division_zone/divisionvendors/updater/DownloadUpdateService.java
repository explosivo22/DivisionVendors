package com.division_zone.divisionvendors.updater;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.BuildConfig;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;

public class DownloadUpdateService extends Service {

    private static final String FILE_NAME = "DivisionVendors.apk";
    public static final String DOWNLOAD_UPDATE_TITLE = "Updating Division Vendors";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            String downloadURL = intent.getStringExtra("downloadURL");
            File newApkFilePath = getExternalFilesDir(null);
            final File newApkFile = new File(newApkFilePath,FILE_NAME);
            newApkFile.setReadable(true, false);
            Uri downloadUri = Uri.fromFile(newApkFile);
            if (Build.VERSION.SDK_INT >= 24){
                downloadUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName(),newApkFile);
            }
            if (newApkFile.exists())
                newApkFile.delete();
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
            request.setTitle(DOWNLOAD_UPDATE_TITLE);

            //set destination
            request.setDestinationUri(downloadUri);

            // get download service and enqueue file
            final DownloadManager manager = (DownloadManager) this.getBaseContext().getSystemService(Context.DOWNLOAD_SERVICE);
            final long startedDownloadId = manager.enqueue(request);

            //set BroadcastReceiver to install app when .apk is downloaded
            final Uri finalDownloadUri = downloadUri;
            BroadcastReceiver onComplete = new BroadcastReceiver() {
                public void onReceive(Context ctxt, Intent intent) {
                    long finishedDownloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    if (startedDownloadId == finishedDownloadId) {

                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(finishedDownloadId);
                        Cursor cursor = manager.query(query);
                        if (cursor.moveToFirst()) {
                            int columnIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            int status = cursor.getInt(columnIndex);

                            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                                //open the downloaded file
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    Intent install = new Intent(Intent.ACTION_INSTALL_PACKAGE);
                                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
                                    install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    install.setDataAndType(finalDownloadUri, manager.getMimeTypeForDownloadedFile(startedDownloadId));
                                    ctxt.startActivity(install);
                                } else {
                                    Intent install = new Intent(Intent.ACTION_VIEW);
                                    install.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    install.setDataAndType(finalDownloadUri, manager.getMimeTypeForDownloadedFile(startedDownloadId));
                                    ctxt.startActivity(install);
                                }

                            } else if (status == DownloadManager.STATUS_FAILED) {
                                if (newApkFile.exists())
                                    newApkFile.delete();
                            }
                        } else {
                            //Delete the partially downloaded file
                            if (newApkFile.exists())
                                newApkFile.delete();
                        }

                        ctxt.unregisterReceiver(this);
                        stopSelf();
                    }
                }
            };

            this.registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
