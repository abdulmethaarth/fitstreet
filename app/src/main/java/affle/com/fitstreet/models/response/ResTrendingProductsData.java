package affle.com.fitstreet.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by akash on 18/7/16.
 */
public class ResTrendingProductsData {
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("productType")
    @Expose
    private String productType;

    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("favourite")
    @Expose
    private Integer favourite;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @SerializedName("discount")
    @Expose
    private String discount;

    /**
     * @return The productID
     */
    public String getProductID() {
        return productID;
    }

    /**
     * @param productID The productID
     */
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }


    /**
     *
     * @return
     * The productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     *
     * @param productType
     * The productType
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }


    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return The favourite
     */
    public Integer getFavourite() {
        return favourite;
    }

    /**
     * @param favourite The favourite
     */
    public void setFavourite(Integer favourite) {
        this.favourite = favourite;
    }
}
