package foundation.dwarves.findfriends.data.firebase.phonenumberfirebase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.NonNull
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class PhoneNumberFirebase {
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private var verificationId: String = ""
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var auth: FirebaseAuth
    private lateinit var activity: Activity
    private var idToken: String? = ""
    private var phoneNumberLocal: String = ""

    private val checkVerifiedCodePhoneNumberPublishSubject = PublishSubject.create<Boolean>()

    fun checkVerifiedCodePhoneNumber(): PublishSubject<Boolean> = checkVerifiedCodePhoneNumberPublishSubject

    fun sendCodeToPhoneNumber(phoneNumber: String, activity: Activity, context: Context) {
        phoneNumberLocal = phoneNumber
        Log.d("PhoneNumber:", phoneNumber)
        try {
            auth = FirebaseAuth.getInstance()
            this.activity = activity

            callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)
                }

                override fun onVerificationFailed(e: FirebaseException) {
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken?) {
                    this@PhoneNumberFirebase.verificationId = verificationId
                    resendToken = token!!
                    Log.d("TOKEN FIREBASE:", resendToken.toString())
                }
            }
            Log.d("PhoneNumber:", phoneNumber)
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                120,
                TimeUnit.SECONDS,
                activity,
                callbacks
            )
        } catch (e: Exception) {
        }
    }

    fun checkCodeServerFirebase(code: String) {
        Log.d("VerifycationId::", verificationId)
        Log.d("CodeOfUser::", code)

        try {
            val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code)
            signInWithPhoneAuthCredential(credential)
        } catch (e: Exception) {
        }
    }

    fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d("Credential is :", credential.toString())
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity, object : OnCompleteListener<AuthResult> {
                override fun onComplete(@NonNull taskAuth: Task<AuthResult>) {
                    if (taskAuth.isSuccessful()) {

                        val user: FirebaseUser = FirebaseAuth.getInstance().currentUser!!

                        user.getIdToken(true).addOnCompleteListener(object : OnCompleteListener<GetTokenResult> {
                            @SuppressLint("CheckResult")
                            override fun onComplete(@NonNull task: Task<GetTokenResult>) {
                                if (task.isSuccessful) {
                                    idToken = task.result?.token


                                } else {
                                }
                            }
                        })
                    } else {
                        if (taskAuth.getException() is FirebaseAuthInvalidCredentialsException) {

                        }
                    }
                }
            })
    }
}