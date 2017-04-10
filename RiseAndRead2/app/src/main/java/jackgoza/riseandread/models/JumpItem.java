package jackgoza.riseandread.models;

/**
 * Created by jackg on 3/5/2017.
 */

public class JumpItem {
    private String prettyName;
    private String jumpURI;
    private appOrWebsite itemType;

    public JumpItem(String pName, String link, appOrWebsite type) {
        this.prettyName = pName;
        this.jumpURI = link;
        this.itemType = type;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public JumpItem setPrettyName(String prettyName) {
        this.prettyName = prettyName;
        return this;
    }

    public String getJumpURI() {
        return jumpURI;
    }

    public JumpItem setJumpURI(String jumpURI) {
        this.jumpURI = jumpURI;
        return this;
    }

    public appOrWebsite getItemType() {
        return itemType;
    }

    public JumpItem setItemType(appOrWebsite itemType) {
        this.itemType = itemType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        JumpItem jumpItem = (JumpItem) o;

        if (prettyName != null ? !prettyName.equals(jumpItem.prettyName) : jumpItem.prettyName != null)
            return false;
        if (jumpURI != null ? !jumpURI.equals(jumpItem.jumpURI) : jumpItem.jumpURI != null)
            return false;
        return itemType == jumpItem.itemType;

    }

    @Override
    public int hashCode() {
        int result = prettyName != null ? prettyName.hashCode() : 0;
        result = 31 * result + (jumpURI != null ? jumpURI.hashCode() : 0);
        result = 31 * result + (itemType != null ? itemType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JumpItem{" +
                "prettyName='" + prettyName + '\'' +
                ", jumpURI='" + jumpURI + '\'' +
                ", itemType=" + itemType +
                '}';
    }

    public enum appOrWebsite {
        MOBILE_APPLICATION, WEBSITE;
    }
}
