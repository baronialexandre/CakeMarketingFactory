package l3info.projet.cakemarketingfactory.model;

import java.io.Serializable;

public class Votes implements Serializable
{
    private int[] votes;

    public Votes(int productAmount) {
        this.votes = new int[productAmount];
    }

    public void setVote(int productId, int voteAmount) { votes[productId] = voteAmount; }

    public void addVote(int productId, int voteAmount) { votes[productId] += voteAmount; }

    public float getPercentage(int productId)
    {
        float sum = 0;
        for (int vote : votes)
        {
            sum += vote;
        }
        if(sum == 0)
            return 0;
        return (votes[productId]/sum)*100;
    }
}
