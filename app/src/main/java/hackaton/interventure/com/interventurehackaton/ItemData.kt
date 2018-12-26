package hackaton.interventure.com.interventurehackaton

import java.io.Serializable

class ItemData(
    var id: Int = 0,
    var name: String? = null,
    var desc: String? = null,
    var image: String? = null
) : Serializable {

    override fun toString(): String {
        return "Media{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", cardImageUrl='" + image + '\'' +
                '}'
    }

    companion object {
        internal const val serialVersionUID = 727566175075960653L
    }
}