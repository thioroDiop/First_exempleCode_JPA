package co.simplon.springquotesapi.model;

public class Quote {
    private int id;
    private String content;
    private int characterIdx;

    public Quote(int id, String content, int characterIdx) {
        this.id = id;
        this.content = content;
        this.characterIdx = characterIdx;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getCharacterIdx() {
        return characterIdx;
    }

    @Override
    public String toString() {
        return "Quote {" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", characterIdx=" + characterIdx +
                '}';
    }
}
