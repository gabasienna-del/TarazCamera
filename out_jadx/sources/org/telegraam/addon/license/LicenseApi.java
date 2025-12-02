package org.telegraam.addon.license;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.gson.Gson;
import de.robv.android.xposed.XposedBridge;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Signature;
import java.security.cert.CertificateException;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.telegraam.addon.security.OkHttpProvider;
import org.telegraam.addon.util.DeviceId;

/* compiled from: LicenseApi.kt */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\bÆ\u0002\u0018\u00002\u00020\u0001:\u0005\u0015\u0016\u0017\u0018\u0019B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\t\u001a\u00020\nJ.\u0010\u0011\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0014\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi;", "", "()V", "JSON", "", "gson", "Lcom/google/gson/Gson;", "activate", "Lorg/telegraam/addon/license/LicenseApi$ApiResp;", "ctx", "Landroid/content/Context;", "token", "publicKeyPem", "client", "Lokhttp3/OkHttpClient;", "fetchHookConfig", "Lorg/telegraam/addon/license/LicenseApi$HookConfigResp;", "heartbeat", "signature", "nonce", "now", "ActivateReq", "ApiResp", "HeartbeatReq", "HookConfigReq", "HookConfigResp", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes3.dex */
public final class LicenseApi {
    private static final String JSON = "application/json";
    public static final LicenseApi INSTANCE = new LicenseApi();
    private static final Gson gson = new Gson();

    private LicenseApi() {
    }

    /* compiled from: LicenseApi.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi$ActivateReq;", "", "token", "", "device_id", "public_key", "enc_public_key", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getDevice_id", "()Ljava/lang/String;", "getEnc_public_key", "getPublic_key", "getToken", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class ActivateReq {
        private final String device_id;
        private final String enc_public_key;
        private final String public_key;
        private final String token;

        public static /* synthetic */ ActivateReq copy$default(ActivateReq activateReq, String str, String str2, String str3, String str4, int i, Object obj) {
            if ((i & 1) != 0) {
                str = activateReq.token;
            }
            if ((i & 2) != 0) {
                str2 = activateReq.device_id;
            }
            if ((i & 4) != 0) {
                str3 = activateReq.public_key;
            }
            if ((i & 8) != 0) {
                str4 = activateReq.enc_public_key;
            }
            return activateReq.copy(str, str2, str3, str4);
        }

        /* renamed from: component1, reason: from getter */
        public final String getToken() {
            return this.token;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDevice_id() {
            return this.device_id;
        }

        /* renamed from: component3, reason: from getter */
        public final String getPublic_key() {
            return this.public_key;
        }

        /* renamed from: component4, reason: from getter */
        public final String getEnc_public_key() {
            return this.enc_public_key;
        }

        public final ActivateReq copy(String token, String device_id, String public_key, String enc_public_key) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(public_key, "public_key");
            return new ActivateReq(token, device_id, public_key, enc_public_key);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ActivateReq)) {
                return false;
            }
            ActivateReq activateReq = (ActivateReq) other;
            return Intrinsics.areEqual(this.token, activateReq.token) && Intrinsics.areEqual(this.device_id, activateReq.device_id) && Intrinsics.areEqual(this.public_key, activateReq.public_key) && Intrinsics.areEqual(this.enc_public_key, activateReq.enc_public_key);
        }

        public int hashCode() {
            return (((((this.token.hashCode() * 31) + this.device_id.hashCode()) * 31) + this.public_key.hashCode()) * 31) + (this.enc_public_key == null ? 0 : this.enc_public_key.hashCode());
        }

        public String toString() {
            return "ActivateReq(token=" + this.token + ", device_id=" + this.device_id + ", public_key=" + this.public_key + ", enc_public_key=" + this.enc_public_key + ")";
        }

        public ActivateReq(String token, String device_id, String public_key, String enc_public_key) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(public_key, "public_key");
            this.token = token;
            this.device_id = device_id;
            this.public_key = public_key;
            this.enc_public_key = enc_public_key;
        }

        public final String getToken() {
            return this.token;
        }

        public final String getDevice_id() {
            return this.device_id;
        }

        public final String getPublic_key() {
            return this.public_key;
        }

        public final String getEnc_public_key() {
            return this.enc_public_key;
        }
    }

    /* compiled from: LicenseApi.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi$HeartbeatReq;", "", "token", "", "device_id", "nonce", "client_time", "signature", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getClient_time", "()Ljava/lang/String;", "getDevice_id", "getNonce", "getSignature", "getToken", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class HeartbeatReq {
        private final String client_time;
        private final String device_id;
        private final String nonce;
        private final String signature;
        private final String token;

        public static /* synthetic */ HeartbeatReq copy$default(HeartbeatReq heartbeatReq, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
            if ((i & 1) != 0) {
                str = heartbeatReq.token;
            }
            if ((i & 2) != 0) {
                str2 = heartbeatReq.device_id;
            }
            String str6 = str2;
            if ((i & 4) != 0) {
                str3 = heartbeatReq.nonce;
            }
            String str7 = str3;
            if ((i & 8) != 0) {
                str4 = heartbeatReq.client_time;
            }
            String str8 = str4;
            if ((i & 16) != 0) {
                str5 = heartbeatReq.signature;
            }
            return heartbeatReq.copy(str, str6, str7, str8, str5);
        }

        /* renamed from: component1, reason: from getter */
        public final String getToken() {
            return this.token;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDevice_id() {
            return this.device_id;
        }

        /* renamed from: component3, reason: from getter */
        public final String getNonce() {
            return this.nonce;
        }

        /* renamed from: component4, reason: from getter */
        public final String getClient_time() {
            return this.client_time;
        }

        /* renamed from: component5, reason: from getter */
        public final String getSignature() {
            return this.signature;
        }

        public final HeartbeatReq copy(String token, String device_id, String nonce, String client_time, String signature) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(nonce, "nonce");
            Intrinsics.checkNotNullParameter(client_time, "client_time");
            Intrinsics.checkNotNullParameter(signature, "signature");
            return new HeartbeatReq(token, device_id, nonce, client_time, signature);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof HeartbeatReq)) {
                return false;
            }
            HeartbeatReq heartbeatReq = (HeartbeatReq) other;
            return Intrinsics.areEqual(this.token, heartbeatReq.token) && Intrinsics.areEqual(this.device_id, heartbeatReq.device_id) && Intrinsics.areEqual(this.nonce, heartbeatReq.nonce) && Intrinsics.areEqual(this.client_time, heartbeatReq.client_time) && Intrinsics.areEqual(this.signature, heartbeatReq.signature);
        }

        public int hashCode() {
            return (((((((this.token.hashCode() * 31) + this.device_id.hashCode()) * 31) + this.nonce.hashCode()) * 31) + this.client_time.hashCode()) * 31) + this.signature.hashCode();
        }

        public String toString() {
            return "HeartbeatReq(token=" + this.token + ", device_id=" + this.device_id + ", nonce=" + this.nonce + ", client_time=" + this.client_time + ", signature=" + this.signature + ")";
        }

        public HeartbeatReq(String token, String device_id, String nonce, String client_time, String signature) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(nonce, "nonce");
            Intrinsics.checkNotNullParameter(client_time, "client_time");
            Intrinsics.checkNotNullParameter(signature, "signature");
            this.token = token;
            this.device_id = device_id;
            this.nonce = nonce;
            this.client_time = client_time;
            this.signature = signature;
        }

        public final String getToken() {
            return this.token;
        }

        public final String getDevice_id() {
            return this.device_id;
        }

        public final String getNonce() {
            return this.nonce;
        }

        public final String getClient_time() {
            return this.client_time;
        }

        public final String getSignature() {
            return this.signature;
        }
    }

    /* compiled from: LicenseApi.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J7\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0019"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi$ApiResp;", "", "ok", "", "error", "", "expires_at", "enc_base_url", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getEnc_base_url", "()Ljava/lang/String;", "getError", "getExpires_at", "getOk", "()Z", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class ApiResp {
        private final String enc_base_url;
        private final String error;
        private final String expires_at;
        private final boolean ok;

        public static /* synthetic */ ApiResp copy$default(ApiResp apiResp, boolean z, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                z = apiResp.ok;
            }
            if ((i & 2) != 0) {
                str = apiResp.error;
            }
            if ((i & 4) != 0) {
                str2 = apiResp.expires_at;
            }
            if ((i & 8) != 0) {
                str3 = apiResp.enc_base_url;
            }
            return apiResp.copy(z, str, str2, str3);
        }

        /* renamed from: component1, reason: from getter */
        public final boolean getOk() {
            return this.ok;
        }

        /* renamed from: component2, reason: from getter */
        public final String getError() {
            return this.error;
        }

        /* renamed from: component3, reason: from getter */
        public final String getExpires_at() {
            return this.expires_at;
        }

        /* renamed from: component4, reason: from getter */
        public final String getEnc_base_url() {
            return this.enc_base_url;
        }

        public final ApiResp copy(boolean ok, String error, String expires_at, String enc_base_url) {
            return new ApiResp(ok, error, expires_at, enc_base_url);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof ApiResp)) {
                return false;
            }
            ApiResp apiResp = (ApiResp) other;
            return this.ok == apiResp.ok && Intrinsics.areEqual(this.error, apiResp.error) && Intrinsics.areEqual(this.expires_at, apiResp.expires_at) && Intrinsics.areEqual(this.enc_base_url, apiResp.enc_base_url);
        }

        public int hashCode() {
            return (((((Boolean.hashCode(this.ok) * 31) + (this.error == null ? 0 : this.error.hashCode())) * 31) + (this.expires_at == null ? 0 : this.expires_at.hashCode())) * 31) + (this.enc_base_url != null ? this.enc_base_url.hashCode() : 0);
        }

        public String toString() {
            return "ApiResp(ok=" + this.ok + ", error=" + this.error + ", expires_at=" + this.expires_at + ", enc_base_url=" + this.enc_base_url + ")";
        }

        public ApiResp(boolean ok, String error, String expires_at, String enc_base_url) {
            this.ok = ok;
            this.error = error;
            this.expires_at = expires_at;
            this.enc_base_url = enc_base_url;
        }

        public /* synthetic */ ApiResp(boolean z, String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? null : str, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : str3);
        }

        public final boolean getOk() {
            return this.ok;
        }

        public final String getError() {
            return this.error;
        }

        public final String getExpires_at() {
            return this.expires_at;
        }

        public final String getEnc_base_url() {
            return this.enc_base_url;
        }
    }

    /* compiled from: LicenseApi.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0019HÖ\u0001J\t\u0010\u001a\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi$HookConfigReq;", "", "token", "", "device_id", "nonce", "client_time", "signature", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getClient_time", "()Ljava/lang/String;", "getDevice_id", "getNonce", "getSignature", "getToken", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class HookConfigReq {
        private final String client_time;
        private final String device_id;
        private final String nonce;
        private final String signature;
        private final String token;

        public static /* synthetic */ HookConfigReq copy$default(HookConfigReq hookConfigReq, String str, String str2, String str3, String str4, String str5, int i, Object obj) {
            if ((i & 1) != 0) {
                str = hookConfigReq.token;
            }
            if ((i & 2) != 0) {
                str2 = hookConfigReq.device_id;
            }
            String str6 = str2;
            if ((i & 4) != 0) {
                str3 = hookConfigReq.nonce;
            }
            String str7 = str3;
            if ((i & 8) != 0) {
                str4 = hookConfigReq.client_time;
            }
            String str8 = str4;
            if ((i & 16) != 0) {
                str5 = hookConfigReq.signature;
            }
            return hookConfigReq.copy(str, str6, str7, str8, str5);
        }

        /* renamed from: component1, reason: from getter */
        public final String getToken() {
            return this.token;
        }

        /* renamed from: component2, reason: from getter */
        public final String getDevice_id() {
            return this.device_id;
        }

        /* renamed from: component3, reason: from getter */
        public final String getNonce() {
            return this.nonce;
        }

        /* renamed from: component4, reason: from getter */
        public final String getClient_time() {
            return this.client_time;
        }

        /* renamed from: component5, reason: from getter */
        public final String getSignature() {
            return this.signature;
        }

        public final HookConfigReq copy(String token, String device_id, String nonce, String client_time, String signature) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(nonce, "nonce");
            Intrinsics.checkNotNullParameter(client_time, "client_time");
            Intrinsics.checkNotNullParameter(signature, "signature");
            return new HookConfigReq(token, device_id, nonce, client_time, signature);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof HookConfigReq)) {
                return false;
            }
            HookConfigReq hookConfigReq = (HookConfigReq) other;
            return Intrinsics.areEqual(this.token, hookConfigReq.token) && Intrinsics.areEqual(this.device_id, hookConfigReq.device_id) && Intrinsics.areEqual(this.nonce, hookConfigReq.nonce) && Intrinsics.areEqual(this.client_time, hookConfigReq.client_time) && Intrinsics.areEqual(this.signature, hookConfigReq.signature);
        }

        public int hashCode() {
            return (((((((this.token.hashCode() * 31) + this.device_id.hashCode()) * 31) + this.nonce.hashCode()) * 31) + this.client_time.hashCode()) * 31) + this.signature.hashCode();
        }

        public String toString() {
            return "HookConfigReq(token=" + this.token + ", device_id=" + this.device_id + ", nonce=" + this.nonce + ", client_time=" + this.client_time + ", signature=" + this.signature + ")";
        }

        public HookConfigReq(String token, String device_id, String nonce, String client_time, String signature) {
            Intrinsics.checkNotNullParameter(token, "token");
            Intrinsics.checkNotNullParameter(device_id, "device_id");
            Intrinsics.checkNotNullParameter(nonce, "nonce");
            Intrinsics.checkNotNullParameter(client_time, "client_time");
            Intrinsics.checkNotNullParameter(signature, "signature");
            this.token = token;
            this.device_id = device_id;
            this.nonce = nonce;
            this.client_time = client_time;
            this.signature = signature;
        }

        public final String getToken() {
            return this.token;
        }

        public final String getDevice_id() {
            return this.device_id;
        }

        public final String getNonce() {
            return this.nonce;
        }

        public final String getClient_time() {
            return this.client_time;
        }

        public final String getSignature() {
            return this.signature;
        }
    }

    /* compiled from: LicenseApi.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0005HÆ\u0003J7\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0005HÖ\u0001R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/telegraam/addon/license/LicenseApi$HookConfigResp;", "", "ok", "", "target_class", "", "target_method", "error", "(ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getError", "()Ljava/lang/String;", "getOk", "()Z", "getTarget_class", "getTarget_method", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"}, k = 1, mv = {1, 9, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
    public static final /* data */ class HookConfigResp {
        private final String error;
        private final boolean ok;
        private final String target_class;
        private final String target_method;

        public static /* synthetic */ HookConfigResp copy$default(HookConfigResp hookConfigResp, boolean z, String str, String str2, String str3, int i, Object obj) {
            if ((i & 1) != 0) {
                z = hookConfigResp.ok;
            }
            if ((i & 2) != 0) {
                str = hookConfigResp.target_class;
            }
            if ((i & 4) != 0) {
                str2 = hookConfigResp.target_method;
            }
            if ((i & 8) != 0) {
                str3 = hookConfigResp.error;
            }
            return hookConfigResp.copy(z, str, str2, str3);
        }

        /* renamed from: component1, reason: from getter */
        public final boolean getOk() {
            return this.ok;
        }

        /* renamed from: component2, reason: from getter */
        public final String getTarget_class() {
            return this.target_class;
        }

        /* renamed from: component3, reason: from getter */
        public final String getTarget_method() {
            return this.target_method;
        }

        /* renamed from: component4, reason: from getter */
        public final String getError() {
            return this.error;
        }

        public final HookConfigResp copy(boolean ok, String target_class, String target_method, String error) {
            return new HookConfigResp(ok, target_class, target_method, error);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof HookConfigResp)) {
                return false;
            }
            HookConfigResp hookConfigResp = (HookConfigResp) other;
            return this.ok == hookConfigResp.ok && Intrinsics.areEqual(this.target_class, hookConfigResp.target_class) && Intrinsics.areEqual(this.target_method, hookConfigResp.target_method) && Intrinsics.areEqual(this.error, hookConfigResp.error);
        }

        public int hashCode() {
            return (((((Boolean.hashCode(this.ok) * 31) + (this.target_class == null ? 0 : this.target_class.hashCode())) * 31) + (this.target_method == null ? 0 : this.target_method.hashCode())) * 31) + (this.error != null ? this.error.hashCode() : 0);
        }

        public String toString() {
            return "HookConfigResp(ok=" + this.ok + ", target_class=" + this.target_class + ", target_method=" + this.target_method + ", error=" + this.error + ")";
        }

        public HookConfigResp(boolean ok, String target_class, String target_method, String error) {
            this.ok = ok;
            this.target_class = target_class;
            this.target_method = target_method;
            this.error = error;
        }

        public /* synthetic */ HookConfigResp(boolean z, String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(z, (i & 2) != 0 ? null : str, (i & 4) != 0 ? null : str2, (i & 8) != 0 ? null : str3);
        }

        public final boolean getOk() {
            return this.ok;
        }

        public final String getTarget_class() {
            return this.target_class;
        }

        public final String getTarget_method() {
            return this.target_method;
        }

        public final String getError() {
            return this.error;
        }
    }

    private final OkHttpClient client(Context ctx) {
        return OkHttpProvider.INSTANCE.newPinnedClient(ctx);
    }

    public final ApiResp activate(Context ctx, String token, String publicKeyPem) throws NoSuchAlgorithmException, IOException, KeyStoreException, CertificateException, NoSuchProviderException, InvalidAlgorithmParameterException {
        String encPublicKeyPem;
        ApiResp apiResp;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(publicKeyPem, "publicKeyPem");
        String deviceId = DeviceId.INSTANCE.get(ctx);
        KeyHelper.INSTANCE.ensureEncKeyPair();
        try {
            encPublicKeyPem = KeyHelper.INSTANCE.getEncPublicKeyPem();
        } catch (Throwable th) {
            encPublicKeyPem = null;
        }
        String encPub = encPublicKeyPem;
        RequestBody.Companion companion = RequestBody.INSTANCE;
        String json = gson.toJson(new ActivateReq(token, deviceId, publicKeyPem, encPub));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        RequestBody body = companion.create(json, MediaType.INSTANCE.get(JSON));
        String base = OkHttpProvider.INSTANCE.getServerBaseUrl(ctx);
        Request req = new Request.Builder().url(base + "/activate").post(body).build();
        Response responseExecute = client(ctx).newCall(req).execute();
        try {
            Response resp = responseExecute;
            ResponseBody responseBodyBody = resp.body();
            String strString = responseBodyBody != null ? responseBodyBody.string() : null;
            if (strString == null) {
                strString = "";
            }
            String s = strString;
            try {
                Object objFromJson = gson.fromJson(s, (Class<Object>) ApiResp.class);
                Intrinsics.checkNotNull(objFromJson);
                apiResp = (ApiResp) objFromJson;
            } catch (Exception e) {
                apiResp = new ApiResp(false, "bad_response", null, null, 12, null);
            }
            CloseableKt.closeFinally(responseExecute, null);
            return apiResp;
        } finally {
        }
    }

    public final ApiResp heartbeat(Context ctx, String token, String signature, String nonce, String now) throws IOException {
        ApiResp apiResp;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(signature, "signature");
        Intrinsics.checkNotNullParameter(nonce, "nonce");
        Intrinsics.checkNotNullParameter(now, "now");
        String deviceId = DeviceId.INSTANCE.get(ctx);
        RequestBody.Companion companion = RequestBody.INSTANCE;
        String json = gson.toJson(new HeartbeatReq(token, deviceId, nonce, now, signature));
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        RequestBody body = companion.create(json, MediaType.INSTANCE.get(JSON));
        String base = OkHttpProvider.INSTANCE.getServerBaseUrl(ctx);
        Request req = new Request.Builder().url(base + "/heartbeat").post(body).build();
        Response responseExecute = client(ctx).newCall(req).execute();
        try {
            Response resp = responseExecute;
            ResponseBody responseBodyBody = resp.body();
            String strString = responseBodyBody != null ? responseBodyBody.string() : null;
            if (strString == null) {
                strString = "";
            }
            String s = strString;
            try {
                Object objFromJson = gson.fromJson(s, (Class<Object>) ApiResp.class);
                Intrinsics.checkNotNull(objFromJson);
                apiResp = (ApiResp) objFromJson;
            } catch (Exception e) {
                apiResp = new ApiResp(false, "bad_response", null, null, 12, null);
            }
            CloseableKt.closeFinally(responseExecute, null);
            return apiResp;
        } finally {
        }
    }

    public final HookConfigResp fetchHookConfig(Context ctx) {
        Context hostCtx;
        Throwable th;
        String strString;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        try {
            try {
                Context hostCtx2 = ctx.createPackageContext("org.telegraam.addon", 3);
                hostCtx = hostCtx2.createDeviceProtectedStorageContext();
                XposedBridge.log("[lasthope] ⚙ host context (device protected) opened");
            } catch (Throwable e) {
                XposedBridge.log("[lasthope] ⚠ cannot open host context: " + e.getMessage());
                hostCtx = null;
            }
            SharedPreferences prefs = (hostCtx == null ? ctx : hostCtx).getSharedPreferences("license", 0);
            String token = prefs.getString("token", null);
            String str = token;
            if (!(str == null || StringsKt.isBlank(str))) {
                String string = prefs.getString("device_id", null);
                if (string == null) {
                    string = DeviceId.INSTANCE.get(ctx);
                }
                Intrinsics.checkNotNull(string);
                String deviceId = string;
                XposedBridge.log("[lasthope] ✅ token=" + token + " deviceId=" + deviceId);
                String string2 = UUID.randomUUID().toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                String nonce = StringsKt.replace$default(string2, "-", "", false, 4, (Object) null);
                Method $this$fetchHookConfig_u24lambda_u242 = LicenseManager.class.getDeclaredMethod("isoUtcNow", new Class[0]);
                $this$fetchHookConfig_u24lambda_u242.setAccessible(true);
                Object objInvoke = $this$fetchHookConfig_u24lambda_u242.invoke(null, new Object[0]);
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
                String now = (String) objInvoke;
                String msg = token + "|" + deviceId + "|" + nonce + "|" + now;
                Signature $this$fetchHookConfig_u24lambda_u243 = Signature.getInstance("SHA256withECDSA");
                $this$fetchHookConfig_u24lambda_u243.initSign(KeyHelper.INSTANCE.getPrivateKey());
                byte[] bytes = msg.getBytes(Charsets.UTF_8);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                $this$fetchHookConfig_u24lambda_u243.update(bytes);
                String signature = Base64.encodeToString($this$fetchHookConfig_u24lambda_u243.sign(), 2);
                Intrinsics.checkNotNull(signature);
                HookConfigReq reqObj = new HookConfigReq(token, deviceId, nonce, now, signature);
                RequestBody.Companion companion = RequestBody.INSTANCE;
                String json = gson.toJson(reqObj);
                Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
                RequestBody reqBody = companion.create(json, MediaType.INSTANCE.get(JSON));
                String base = OkHttpProvider.INSTANCE.getServerBaseUrl(ctx);
                String url = base + "/hook-config";
                XposedBridge.log("[lasthope] 🌐 POST " + url);
                Request req = new Request.Builder().url(url).post(reqBody).build();
                Response responseExecute = client(ctx).newCall(req).execute();
                try {
                    Response resp = responseExecute;
                    int code = resp.code();
                    ResponseBody responseBodyBody = resp.body();
                    if (responseBodyBody == null) {
                        strString = null;
                    } else {
                        try {
                            strString = responseBodyBody.string();
                        } catch (Throwable th2) {
                            th = th2;
                            try {
                                throw th;
                            } catch (Throwable th3) {
                                CloseableKt.closeFinally(responseExecute, th);
                                throw th3;
                            }
                        }
                    }
                    if (strString == null) {
                        strString = "";
                    }
                    String body = strString;
                    try {
                        XposedBridge.log("[lasthope] 🔎 HTTP " + code + ": " + body);
                        if (!resp.isSuccessful()) {
                            CloseableKt.closeFinally(responseExecute, null);
                            return null;
                        }
                        HookConfigResp hookConfigResp = (HookConfigResp) gson.fromJson(body, HookConfigResp.class);
                        CloseableKt.closeFinally(responseExecute, null);
                        return hookConfigResp;
                    } catch (Throwable th4) {
                        th = th4;
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                }
            } else {
                XposedBridge.log("[lasthope] ❌ no token even in device-protected prefs");
                return null;
            }
        } catch (Throwable t) {
            XposedBridge.log("[lasthope] 💥 fetchHookConfig() exception: " + t.getMessage());
            return null;
        }
    }
}
