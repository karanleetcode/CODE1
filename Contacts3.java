

public class Contacts3{
    int _id;
    String wordName;
    String voice;
    public Contacts3(){
    }
    public Contacts3(int id, String wordName , String voice){
        this._id = id;
        this.wordName = wordName;
        this.voice=voice;
    }
    public int getID(){
        return this._id;
    }
    public void setID(int id){
        this._id = id;
    }

    public String getWordName(){
        return this.wordName;
    }
    public void setWordName(String wordName){
        this.wordName = wordName;
    }

    public String getVoice(){
        return this.voice;
    }
    public void setVoice(String voice){
        this.voice = voice;
    }
}
