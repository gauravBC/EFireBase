package g.efirebase;

/**
 * Created by Hewlett packard on 07-06-2017.
 */
public class ShowDataItems {
    private String Image_URL,Image_Title,Image_Thoughts;  //put this name same as Database Fields

    public ShowDataItems(String image_URL, String image_title ,String image_Thoughts) {
        Image_URL = image_URL;
        Image_Title = image_title;
        Image_Thoughts=image_Thoughts;
    }
    public ShowDataItems()
    {
        //Empty Constructor Needed
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getImage_Title() {
        return Image_Title;
    }

    public void setTitle(String title) {
        Image_Title = title;

    }
    public String getImage_Thoughts() {
        return Image_Thoughts;
    }

    public void setThoughts(String thoughts) {
        Image_Thoughts = thoughts;

    }

}