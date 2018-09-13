/*
 * Copyright (c) 2018 noriokun4649.
 */

package jp.noriokun4649.comicmarketmap.twitter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.Toast;

import com.kazakago.cryptore.CipherAlgorithm;
import com.kazakago.cryptore.Cryptore;
import com.kazakago.cryptore.DecryptResult;
import com.kazakago.cryptore.EncryptResult;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableEntryException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.NoSuchPaddingException;

import jp.noriokun4649.comicmarketmap.R;
import jp.noriokun4649.comicmarketmap.dialogfragment.FragmentCancelOKAlertDialog;
import twitter4j.AsyncTwitter;
import twitter4j.AsyncTwitterFactory;
import twitter4j.TwitterAdapter;
import twitter4j.TwitterException;
import twitter4j.TwitterListener;
import twitter4j.TwitterMethod;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by noriokun4649 on 2017/03/10.
 */

public class TwitterConnect implements Serializable {
    /**
     * ハンドラー.
     * このインスタンスを通して、じゃないとアプリの画面等を操作できません.
     */
    private static Handler mHandler = new Handler();
    /**
     * api キー.
     */
    private final String apiKey = getSkebeWord();
    /**
     * api シークレット.
     */
    private final String apiSecret = getEroWord();
    /**
     * コンテキスト.
     * キャストしてサポートライブラリ使用可能なアクティビティ.
     */
    private AppCompatActivity context;
    /**
     * とーくん.
     */
    private String tokens;
    /**
     * とーくんシークレット.
     */
    private String tokensSecret;
    /**
     * リクエストとーくん.
     */
    private RequestToken mReqToken;
    /**
     * Twitterのリスナー.
     */
    private final TwitterListener mListener = new TwitterAdapter() {
        @Override
        public void verifiedCredentials(final User user) {
            super.verifiedCredentials(user);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    sharedPreferences.edit().putString("scren_name", user.getScreenName()).apply();
                }
            });
        }

        @Override
        public void gotUserDetail(final User user) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    sharedPreferences.edit().putString("scren_name", user.getScreenName()).apply();


                }
            });
            super.gotUserDetail(user);
        }

        @Override
        public void gotOAuthRequestToken(final RequestToken token) {
            mReqToken = token;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mReqToken.getAuthenticationURL()));
            context.startActivity(intent);
            //Toast.makeText(context.getApplicationContext(), "token:" + token.getAuthorizationURL(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void gotOAuthAccessToken(final AccessToken token) {
            tokens = token.getToken();
            tokensSecret = token.getTokenSecret();
            try {
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).edit();
                editor.putString("tokens", encrypt(tokens)).apply();
                editor.putString("tokens_secret", encrypt(tokensSecret)).apply();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onException(final TwitterException te, final TwitterMethod method) { //super.onException(te, method);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, te.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            //Log.d("A", "a:" + te.getErrorMessage());
        }
    };
    /**
     * ふらぐ.
     * ONの場合はTwitterにログイン中.
     */
    private boolean flag = false;
    /**
     * 非同期処理のTwitterのコンストラクタ.
     */
    private AsyncTwitter mTwitter;

    /**
     * アクティビティから呼ばれた場合のコンストラクタ.
     *
     * @param activity アクティビティ
     */
    public TwitterConnect(final AppCompatActivity activity) {
        context = activity;
    }

    /*
    /**
     * アクティビティから呼ばれた場合のコンストラクタ.
     *
     * @param activity アクティビティ
     */
    /*
    public TwitterConnect(final Activity activity) {
        context = (AppCompatActivity) activity;
    }
    */

    /**
     * TwitterとOAuth認証を行う際のメソッド.
     */
    public void signIn() {
        if (!flag) {
            Toast.makeText(context.getApplicationContext(), R.string.twitter_sinin, Toast.LENGTH_LONG).show();
            mTwitter.addListener(mListener);
            mTwitter.getOAuthRequestTokenAsync("comiketwittercallback://callback");
        } else {
            FragmentCancelOKAlertDialog fragmentCancelOKAlertDialog = new FragmentCancelOKAlertDialog();
            Bundle bundle = new Bundle();
            bundle.putInt("title", R.string.this_connect_now);
            bundle.putString("massage", context.getString(R.string.this_connect_now_mass));
            bundle.putInt("buttonName", R.string.dis_connect);
            fragmentCancelOKAlertDialog.setArguments(bundle);
            fragmentCancelOKAlertDialog.show(context.getSupportFragmentManager(), "disconnect");
        }
    }

    /**
     * loginメソッド.
     */
    public void login() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String tokens2 = sharedPreferences.getString("tokens", null);
        String tokensSecrets = sharedPreferences.getString("tokens_secret", null);
        mTwitter = new AsyncTwitterFactory().getInstance();
        flag = false;
        mTwitter.setOAuthConsumer(apiKey, apiSecret);
        if ((tokens2 != null) && (tokensSecrets != null)) {
            try {
                tokens2 = decrypt(tokens2);
                tokensSecrets = decrypt(tokensSecrets);
            } catch (Exception e) {
                e.printStackTrace();
            }
            mTwitter.setOAuthAccessToken(new AccessToken(tokens2, tokensSecrets));
            mTwitter.verifyCredentials();
            flag = true;
        }
    }

    /**
     * Twitterとの連携を解除するメソッド.
     * このメソッドで連携を解除し、各トークンの情報を削除する。
     */
    public void logout() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("tokens").apply();
        editor.remove("tokens_secret").apply();
        editor.remove("scren_name").apply();
        getmTwitter().setOAuthAccessToken(null);
        flag = false;
        //Toast.makeText(context, "ログアウト適用のためアプリ再起動します", Toast.LENGTH_SHORT).show();
    }

    /**
     * TwitterとOAuth認証をした際にブラウザ等からのコールバックを受け取るメソッド.
     *
     * @param verifier コールバック時に受け取った認証鍵?
     */
    public void getOAuthAsync(final String verifier) {
        mTwitter.getOAuthAccessTokenAsync(mReqToken, verifier);
    }

    /**
     * APIKEYを取得するメソッド.
     *
     * @return APIKEY
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * APISECRETを取得するメソッド.
     *
     * @return getApiSECRET
     */
    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * Twitterと連携中かどうかを返すメソッド.
     *
     * @return true:連携中、false:連携してない
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Twitterと連携中かどうかを設定.
     *
     * @param in true:連携中、false:連携してない
     */
    public void setFlag(final boolean in) {
        flag = in;
    }

    /**
     * 非同期処理を行う際に必要になるインスタンスを返すメソッド.
     *
     * @return 非同期処理のTwitter
     */
    public AsyncTwitter getmTwitter() {
        return mTwitter;
    }

    /**
     * 使われてないメソッドだおぉーーん.
     *
     * @return しらん
     */
    public Configuration getConfiguration() {
        return new ConfigurationBuilder().setOAuthConsumerKey(apiKey)
                .setOAuthConsumerSecret(apiSecret)
                .setOAuthAccessToken(tokens)
                .setOAuthAccessTokenSecret(tokensSecret)
                .build();
    }

    /**
     * AndroidKeyStoreを使用した暗号化プロセスの初期化処理です.
     *
     * @return 暗号化・複合化を行う際に利用するビルダーを返します。
     * @throws CertificateException               CertificateException
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws KeyStoreException                  KeyStoreException
     * @throws NoSuchProviderException            NoSuchProviderException
     * @throws InvalidAlgorithmParameterException InvalidAlgorithmParameterException
     * @throws IOException                        IOException
     */
    private Cryptore getCryptore() throws CertificateException, NoSuchPaddingException, NoSuchAlgorithmException, KeyStoreException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException {
        Cryptore.Builder builder = new Cryptore.Builder("CIPHER_RSA", CipherAlgorithm.RSA);
        builder.setContext(context);
        return builder.build();
    }

    /**
     * 実際に暗号化を処理するメソッドです.
     *
     * @param plainStr 暗号化対象の文字列
     * @return 暗号化後の文字列
     * @throws CertificateException               CertificateException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws KeyStoreException                  KeyStoreException
     * @throws NoSuchProviderException            NoSuchProviderException
     * @throws InvalidAlgorithmParameterException InvalidAlgorithmParameterException
     * @throws IOException                        IOException
     * @throws UnrecoverableEntryException        UnrecoverableEntryException
     * @throws InvalidKeyException                InvalidKeyException
     */
    private String encrypt(final String plainStr) throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException,
            KeyStoreException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException, UnrecoverableEntryException, InvalidKeyException {
        byte[] plainByte = plainStr.getBytes();
        EncryptResult result = getCryptore().encrypt(plainByte);
        return Base64.encodeToString(result.getBytes(), Base64.DEFAULT);
    }

    /**
     * 実際に複合化を処理するメソッドです.
     *
     * @param encryptedStr 複合化対象の文字列
     * @return 複合後の文字列
     * @throws CertificateException               CertificateException
     * @throws NoSuchAlgorithmException           NoSuchAlgorithmException
     * @throws NoSuchPaddingException             NoSuchPaddingException
     * @throws KeyStoreException                  KeyStoreException
     * @throws NoSuchProviderException            NoSuchProviderException
     * @throws InvalidAlgorithmParameterException InvalidAlgorithmParameterException
     * @throws IOException                        IOException
     * @throws UnrecoverableKeyException          UnrecoverableEntryException
     * @throws InvalidKeyException                InvalidKeyException
     */
    private String decrypt(final String encryptedStr) throws CertificateException, NoSuchAlgorithmException, NoSuchPaddingException, KeyStoreException,
            NoSuchProviderException, InvalidAlgorithmParameterException, IOException, UnrecoverableKeyException, InvalidKeyException {
        byte[] encryptedByte = Base64.decode(encryptedStr, Base64.DEFAULT);
        DecryptResult result = getCryptore().decrypt(encryptedByte, null);
        return new String(result.getBytes());
    }

    /**
     * スケベなWordを返します.
     *
     * @return Veryスケベ
     */
    private String getSkebeWord() {
        return (new Object() {
            private int t;

            public String toString() {
                byte[] buf = new byte[25];
                t = -193101372;
                buf[0] = (byte) (t >>> 3);
                t = -814718947;
                buf[1] = (byte) (t >>> 16);
                t = -1322202849;
                buf[2] = (byte) (t >>> 15);
                t = -4798253;
                buf[3] = (byte) (t >>> 15);
                t = 39350931;
                buf[4] = (byte) (t >>> 14);
                t = -787258252;
                buf[5] = (byte) (t >>> 18);
                t = 188173861;
                buf[6] = (byte) (t >>> 12);
                t = -690205048;
                buf[7] = (byte) (t >>> 17);
                t = 1284936581;
                buf[8] = (byte) (t >>> 17);
                t = -850543776;
                buf[9] = (byte) (t >>> 7);
                t = 804548638;
                buf[10] = (byte) (t >>> 8);
                t = 715769012;
                buf[11] = (byte) (t >>> 4);
                t = 1367901543;
                buf[12] = (byte) (t >>> 19);
                t = -535808530;
                buf[13] = (byte) (t >>> 7);
                t = -1932628556;
                buf[14] = (byte) (t >>> 2);
                t = -155668814;
                buf[15] = (byte) (t >>> 1);
                t = 1525542156;
                buf[16] = (byte) (t >>> 2);
                t = -1481943264;
                buf[17] = (byte) (t >>> 13);
                t = 542695605;
                buf[18] = (byte) (t >>> 10);
                t = -590695462;
                buf[19] = (byte) (t >>> 11);
                t = 1547258603;
                buf[20] = (byte) (t >>> 8);
                t = 954418793;
                buf[21] = (byte) (t >>> 12);
                t = -462807763;
                buf[22] = (byte) (t >>> 2);
                t = -1023304898;
                buf[23] = (byte) (t >>> 7);
                t = -590385063;
                buf[24] = (byte) (t >>> 17);
                return new String(buf);
            }
        }.toString());
    }

    /**
     * エッチなWordを返します.
     *
     * @return Veryエッチ
     */
    private String getEroWord() {
        return (new Object() {
            private int t;

            public String toString() {
                byte[] buf = new byte[50];
                t = 1163326861;
                buf[0] = (byte) (t >>> 20);
                t = 960762100;
                buf[1] = (byte) (t >>> 23);
                t = 1000856169;
                buf[2] = (byte) (t >>> 6);
                t = 1272689934;
                buf[3] = (byte) (t >>> 24);
                t = -1462006939;
                buf[4] = (byte) (t >>> 11);
                t = -1273851120;
                buf[5] = (byte) (t >>> 23);
                t = -1663677655;
                buf[6] = (byte) (t >>> 14);
                t = -369308084;
                buf[7] = (byte) (t >>> 10);
                t = 835693764;
                buf[8] = (byte) (t >>> 23);
                t = 1195272966;
                buf[9] = (byte) (t >>> 5);
                t = 960699793;
                buf[10] = (byte) (t >>> 11);
                t = 988459446;
                buf[11] = (byte) (t >>> 11);
                t = 1152575189;
                buf[12] = (byte) (t >>> 9);
                t = -355357553;
                buf[13] = (byte) (t >>> 1);
                t = 53181547;
                buf[14] = (byte) (t >>> 19);
                t = 951808563;
                buf[15] = (byte) (t >>> 15);
                t = 1079519778;
                buf[16] = (byte) (t >>> 7);
                t = -698006567;
                buf[17] = (byte) (t >>> 20);
                t = 1583811686;
                buf[18] = (byte) (t >>> 17);
                t = 107801711;
                buf[19] = (byte) (t >>> 21);
                t = 914522781;
                buf[20] = (byte) (t >>> 24);
                t = 1180722732;
                buf[21] = (byte) (t >>> 17);
                t = 171510227;
                buf[22] = (byte) (t >>> 10);
                t = -1289246888;
                buf[23] = (byte) (t >>> 20);
                t = 779945592;
                buf[24] = (byte) (t >>> 4);
                t = -1682876857;
                buf[25] = (byte) (t >>> 6);
                t = 1734008616;
                buf[26] = (byte) (t >>> 9);
                t = -911518558;
                buf[27] = (byte) (t >>> 15);
                t = -779347764;
                buf[28] = (byte) (t >>> 22);
                t = 657005421;
                buf[29] = (byte) (t >>> 10);
                t = 1243628416;
                buf[30] = (byte) (t >>> 24);
                t = 718421316;
                buf[31] = (byte) (t >>> 19);
                t = 1068388926;
                buf[32] = (byte) (t >>> 3);
                t = -1118629529;
                buf[33] = (byte) (t >>> 14);
                t = -909316838;
                buf[34] = (byte) (t >>> 9);
                t = -941971975;
                buf[35] = (byte) (t >>> 14);
                t = 292713053;
                buf[36] = (byte) (t >>> 3);
                t = -2062124540;
                buf[37] = (byte) (t >>> 14);
                t = -461255658;
                buf[38] = (byte) (t >>> 6);
                t = -700173120;
                buf[39] = (byte) (t >>> 7);
                t = 178514638;
                buf[40] = (byte) (t >>> 21);
                t = -1079193836;
                buf[41] = (byte) (t >>> 15);
                t = 483908539;
                buf[42] = (byte) (t >>> 22);
                t = -1735868323;
                buf[43] = (byte) (t >>> 10);
                t = 1825567848;
                buf[44] = (byte) (t >>> 4);
                t = 1330626413;
                buf[45] = (byte) (t >>> 16);
                t = -1294946676;
                buf[46] = (byte) (t >>> 1);
                t = 508349205;
                buf[47] = (byte) (t >>> 3);
                t = -1445617615;
                buf[48] = (byte) (t >>> 14);
                t = -1875215513;
                buf[49] = (byte) (t >>> 11);
                return new String(buf);
            }
        }.toString());
    }

}
