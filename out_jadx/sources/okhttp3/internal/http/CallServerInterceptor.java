package okhttp3.internal.http;

import androidx.constraintlayout.widget.ConstraintLayout;
import kotlin.Metadata;
import okhttp3.Interceptor;

/* compiled from: CallServerInterceptor.kt */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, d2 = {"Lokhttp3/internal/http/CallServerInterceptor;", "Lokhttp3/Interceptor;", "forWebSocket", "", "(Z)V", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "shouldIgnoreAndWaitForRealResponse", "code", "", "okhttp"}, k = 1, mv = {1, 8, 0}, xi = ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE)
/* loaded from: classes8.dex */
public final class CallServerInterceptor implements Interceptor {
    private final boolean forWebSocket;

    public CallServerInterceptor(boolean forWebSocket) {
        this.forWebSocket = forWebSocket;
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00ee A[Catch: IOException -> 0x01de, TRY_LEAVE, TryCatch #6 {IOException -> 0x01de, blocks: (B:41:0x00d7, B:43:0x00ee), top: B:110:0x00d7 }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x015d A[Catch: IOException -> 0x01da, TryCatch #1 {IOException -> 0x01da, blocks: (B:59:0x0143, B:63:0x014e, B:65:0x016d, B:67:0x017e, B:74:0x0195, B:76:0x019b, B:80:0x01a8, B:82:0x01c5, B:83:0x01cd, B:84:0x01d8, B:69:0x018a, B:64:0x015d), top: B:100:0x0143 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x018a A[Catch: IOException -> 0x01da, TryCatch #1 {IOException -> 0x01da, blocks: (B:59:0x0143, B:63:0x014e, B:65:0x016d, B:67:0x017e, B:74:0x0195, B:76:0x019b, B:80:0x01a8, B:82:0x01c5, B:83:0x01cd, B:84:0x01d8, B:69:0x018a, B:64:0x015d), top: B:100:0x0143 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0195 A[Catch: IOException -> 0x01da, TryCatch #1 {IOException -> 0x01da, blocks: (B:59:0x0143, B:63:0x014e, B:65:0x016d, B:67:0x017e, B:74:0x0195, B:76:0x019b, B:80:0x01a8, B:82:0x01c5, B:83:0x01cd, B:84:0x01d8, B:69:0x018a, B:64:0x015d), top: B:100:0x0143 }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x019b A[Catch: IOException -> 0x01da, TryCatch #1 {IOException -> 0x01da, blocks: (B:59:0x0143, B:63:0x014e, B:65:0x016d, B:67:0x017e, B:74:0x0195, B:76:0x019b, B:80:0x01a8, B:82:0x01c5, B:83:0x01cd, B:84:0x01d8, B:69:0x018a, B:64:0x015d), top: B:100:0x0143 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01a0  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01a8 A[Catch: IOException -> 0x01da, TryCatch #1 {IOException -> 0x01da, blocks: (B:59:0x0143, B:63:0x014e, B:65:0x016d, B:67:0x017e, B:74:0x0195, B:76:0x019b, B:80:0x01a8, B:82:0x01c5, B:83:0x01cd, B:84:0x01d8, B:69:0x018a, B:64:0x015d), top: B:100:0x0143 }] */
    @Override // okhttp3.Interceptor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public okhttp3.Response intercept(okhttp3.Interceptor.Chain r22) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 507
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.http.CallServerInterceptor.intercept(okhttp3.Interceptor$Chain):okhttp3.Response");
    }

    private final boolean shouldIgnoreAndWaitForRealResponse(int code) {
        if (code == 100) {
            return true;
        }
        return 102 <= code && code < 200;
    }
}
