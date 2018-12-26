package hackaton.interventure.com.interventurehackaton

import java.io.Serializable

data class ItemData(
    var id: Int = 0,
    var name: String? = null,
    var desc: String? = null,
    var image: String? = null,
    var background: String? = null,
    val videoUrl: String? = null
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