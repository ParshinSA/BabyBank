package com.example.babybank.data.data_source.local

import android.content.Context
import android.net.Uri
import com.example.babybank.data.data_source.interf.UserInfoSharedPrefsDataSource
import io.reactivex.Completable
import io.reactivex.Single
import java.io.IOException
import javax.inject.Inject

class UserInfoSharedPrefsDataSourceImpl @Inject constructor(
    context: Context
) : UserInfoSharedPrefsDataSource {

    private val mPreferences =
        context.getSharedPreferences(SHARED_PREF_USER_INFO, Context.MODE_PRIVATE)

    override fun getCustomAvatarLink(): Single<String?> {
        return Single.create { emitter ->
            try {
                val avatarLink = mPreferences.getString(KEY_AVATAR_LINK, "")
                if (avatarLink != null) emitter.onSuccess(avatarLink)
                else emitter.onError(IOException("Not found $KEY_AVATAR_LINK"))
            } catch (t: Throwable) {
                emitter.onError(IOException("Failed get $KEY_AVATAR_LINK"))
            }
        }
    }

    override fun saveCustomAvatarLink(uri: Uri): Completable {
        return Completable.create { emitter ->
            try {
                mPreferences.edit()
                    .putString(KEY_AVATAR_LINK, uri.toString())
                    .apply()

                emitter.onComplete()
            } catch (t: Throwable) {
                emitter.onError(IOException("Failed save $KEY_AVATAR_LINK"))
            }
        }
    }

    companion object {
        private const val SHARED_PREF_USER_INFO = "SHARED_PREF_USER_INFO"
        private const val KEY_AVATAR_LINK = "KEY_AVATAR_LINK"
    }
}