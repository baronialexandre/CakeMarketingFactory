package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;

public class TopPlayer implements Serializable {
    private int userId;
    private int rank;

    public TopPlayer(int userId, int rank)
    {
        this.userId = userId;
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
