package jackgoza.riseandread.models;

import java.util.Objects;

/**
 * Created by jackg on 3/5/2017.
 */

public class JumpItem {
    private String prettyName;
    private String weblinkURI;
    private String applinkURI;

    public String getPrettyName() {
        return prettyName;
    }

    public JumpItem setPrettyName(String prettyName) {
        this.prettyName = prettyName;
        return this;
    }

    public String getWeblinkURI() {
        return weblinkURI;
    }

    public JumpItem setWeblinkURI(String weblinkURI) {
        this.weblinkURI = weblinkURI;
        return this;
    }

    public String getApplinkURI() {
        return applinkURI;
    }

    public JumpItem setApplinkURI(String applinkURI) {
        this.applinkURI = applinkURI;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JumpItem jumpItem = (JumpItem) o;
        return Objects.equals(prettyName, jumpItem.prettyName) &&
                Objects.equals(weblinkURI, jumpItem.weblinkURI) &&
                Objects.equals(applinkURI, jumpItem.applinkURI);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prettyName, weblinkURI, applinkURI);
    }
}
