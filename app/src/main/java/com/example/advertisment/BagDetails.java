package com.example.advertisment;

public class BagDetails {
    public String designNameOfBag;
    public String priceOfBag;
    public String imageURL;
    public BagDetails(){};
    public BagDetails(String designNameOfBag,String priceOfBag,String imageURL){
        this.designNameOfBag=designNameOfBag;
        this.priceOfBag=priceOfBag;
        this.imageURL=imageURL;
    }


    public String getDesignNameOfBag() {
        return designNameOfBag;
    }

    public void setDesignNameOfBag(String designNameOfBag) {
        this.designNameOfBag = designNameOfBag;
    }

    public String getPriceOfBag() {
        return priceOfBag;
    }

    public void setPriceOfBag(String priceOfBag) {
        this.priceOfBag = priceOfBag;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
