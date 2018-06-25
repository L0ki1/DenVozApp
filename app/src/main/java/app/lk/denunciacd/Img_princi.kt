package app.lk.denunciacd

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Img_princi  {

    var url: String=""
    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(url: String) {
        this.url  = url
    }

}