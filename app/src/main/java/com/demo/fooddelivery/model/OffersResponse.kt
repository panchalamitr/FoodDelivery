import com.google.gson.annotations.SerializedName

data class OffersResponse (
	@SerializedName("status") val status : String,
	@SerializedName("offers") val offers : List<String>
)