package com.brikmas.quranapp.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.brikmas.quranapp.R
import com.brikmas.quranapp.model.Para
import com.brikmas.quranapp.model.Safa
import com.brikmas.quranapp.util.Constants
import com.brikmas.quranapp.util.Resource
import com.brikmas.quranapp.util.ResourceState
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.*
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import org.imperiumlabs.geofirestore.GeoFirestore
import org.imperiumlabs.geofirestore.GeoQuery


class RemoteRepository{

    var TAG = "RemoteRepository"

    var geoFirestore: GeoFirestore? = null
    var geoQuery: GeoQuery? = null
    var db: FirebaseDatabase? = null
    var firestoreDB: FirebaseFirestore? = null
    var firebaseStorage: FirebaseStorage? = null
    var gson: Gson? = null

    init {
        firestoreDB = FirebaseFirestore.getInstance()
        db = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()
        gson = Gson()
    }

    companion object{
        fun provideRemoteRepository(): RemoteRepository{
            return RemoteRepository()
        }
    }

    /*
    @downloadUploadFiles get files form storage then uploads to database/firestore with image references.
    paraNo is number to refernce the number of para e-g  پارہ1
    */
    fun downloadUploadFiles(paraNo: String) {
        val storageRef = firebaseStorage!!.getReference(Constants.FIREBASE_STORAGE).child("پارہ $paraNo")

        storageRef.listAll().addOnSuccessListener { result ->
            for (fileRef in result.items) {
                fileRef.downloadUrl.addOnSuccessListener {

                    var fileName = fileRef.name
                    var childName = fileName.replace(".jpeg", "صفحہ")
                    var para = Safa(
                            fileName.replace(".jpeg", " صفحہ"),
                            it.toString()
                    )

                    db!!.getReference(Constants.FIRESTORE_COLLECTION).child("پارہ $paraNo").child(childName).setValue(para).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.e("FireDatabase", fileRef.path + " -> uploaded")
                        }
                    }

                    firestoreDB!!.collection(Constants.FIRESTORE_COLLECTION).document(Constants.FIRESTORE_DOCUMENT).collection("پارہ $paraNo").document(childName).set(para).addOnCompleteListener {
                        if (it.isSuccessful) {
                            Log.e("Firestore", fileRef.path + " -> uploaded")
                        }
                    }
                }.addOnFailureListener {
                    Log.e("Quran", fileRef.path + " -> Task fialed")
                }
            }
        }.addOnFailureListener {
            // Handle any errors
        }
    }

    /*
      @loadParas return Paras name, id, image.
      */
    fun loadParas(): MutableLiveData<Resource<List<Para>>>{
        val parasLiveData = MutableLiveData<Resource<List<Para>>>()
        var list = arrayListOf<Para>()
        //10
        list.add(Para("پارہ 1","الم", R.drawable.quran_logo_150))
        list.add(Para("پارہ 2","سَيَقُولُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 3","تِلْكَ الرُّسُلُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 4","لَنْ تَنَالُوا", R.drawable.quran_logo_150))
        list.add(Para("پارہ 5","وَالْمُحْصَنَاتُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 6","لَا يُحِبُّ اللَّهُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 7","وَإِذَا سَمِعُوا", R.drawable.quran_logo_150))
        list.add(Para("پارہ 8","وَلَوْ أَنَّنَا", R.drawable.quran_logo_150))
        list.add(Para("پارہ 9","قَالَ الْمَلَأُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 10","وَاعْلَمُوا", R.drawable.quran_logo_150))
        //20
        list.add(Para("پارہ 11","يَعْتَذِرُونَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 12","وَمَا مِنْ دَابَّةٍ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 13","وَمَا أُبَرِّئُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 14","رُبَمَا", R.drawable.quran_logo_150))
        list.add(Para("پارہ 15","سُبْحَانَ الَّذِي", R.drawable.quran_logo_150))
        list.add(Para("پارہ 16","قَالَ أَلَمْ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 17","قْتَرَبَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 18","قَدْ أَفْلَحَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 19","وَقَالَ الَّذِينَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 20","أَمَّنْ خَلَقَ", R.drawable.quran_logo_150))
        //30
        list.add(Para("پارہ 21","اتْلُ مَا أُوحِيَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 22","وَمَنْ يَقْنُتْ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 23","وَمَا لِيَ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 24","فَمَنْ أَظْلَمُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 25","إِلَيْهِ يُرَدُّ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 26","حم", R.drawable.quran_logo_150))
        list.add(Para("پارہ 27","قَالَ فَمَا خَطْبُكُمْ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 28","قَدْ سَمِعَ اللَّهُ", R.drawable.quran_logo_150))
        list.add(Para("پارہ 29","تَبَارَكَ الَّذِي", R.drawable.quran_logo_150))
        list.add(Para("پارہ 30","عَمَّ يَتَسَاءَلُونَ", R.drawable.quran_logo_150))

        parasLiveData.value = Resource.Paras(ResourceState.PARAS,list,"Quran paras")

        return parasLiveData
    }

    fun getSafasOfPara(safasLiveData: MutableLiveData<Resource<List<Safa>>>, paraId: String) {
        firestoreDB!!.
        collection(Constants.FIRESTORE_COLLECTION).
        document(Constants.FIRESTORE_DOCUMENT).
        collection(paraId).get().addOnCompleteListener {
            if (it.result!!.documents.size > 0) {
                var list = arrayListOf<Safa>()
                for (doc in it.result!!.documents) {
                    if (doc.exists()){
                        var name = doc.getString("name").toString()
                        var id = name.substring(0, name.indexOf(" "))
                        var safa = Safa(
                            id,
                            name,
                            doc.getString("image").toString(),
                            doc.getDate("created_at")
                        )
                        list.add(safa)
                    }
                }
                safasLiveData.value = Resource.Safas(ResourceState.SAFAS, list, "Fetched all data")
            }else{
                safasLiveData.value = Resource.Error(ResourceState.ERROR, null, "No safas found in $paraId")
            }
        }.addOnFailureListener {
            Log.e(TAG,it.message.toString())
        }
    }
}