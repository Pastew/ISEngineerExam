package com.pastew.isexam.favourites;

import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Pastew on 2016-01-18.
 */
public class FavouritesQuestions {

    private SharedPreferences sp;

    public FavouritesQuestions(SharedPreferences sharedPreferences){
        this.sp = sharedPreferences;
    }

    public void removeQuestionFromFavourites(int questionId) {
        ArrayList<Integer> savedList = getFavouritesQuestionsList();
        for(int i = 0 ; i < savedList.size() ; ++i)
            if(savedList.get(i).equals(questionId))
                savedList.remove(i);

        saveFavouritesQuestions(savedList);
    }

    public boolean isQuestionFavourite(int questionsId){
        ArrayList<Integer> savedList = getFavouritesQuestionsList();
        for(int id : savedList)
            if(questionsId == id)
                return true;
        return false;
    }

    public void addQuestionToFavourites(int questionsId) {
        ArrayList<Integer> savedList = getFavouritesQuestionsList();
        if(!savedList.contains(questionsId))
            savedList.add(questionsId);

        saveFavouritesQuestions(savedList);
    }

    public ArrayList<Integer> getFavouritesQuestionsList() {
        String savedString = sp.getString("favourites", "");
        String[] questionsStr = savedString.split(",");
        ArrayList<Integer> savedList = new ArrayList<>();
        if(questionsStr[0].equals(""))
            return savedList;

        for (String idStr: questionsStr) {
            savedList.add(Integer.parseInt(idStr));
        }
        return savedList;
    }

    public void saveFavouritesQuestions(ArrayList<Integer> savedList) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < savedList.size(); i++) {
            str.append(savedList.get(i)).append(",");
        }
        sp.edit().putString("favourites", str.toString()).commit();
    }

    public int getFavouritesQuestionsNumber(){
        return getFavouritesQuestionsList().size();
    }

    public int[] getFavouritesQuestions(){
        return convertIntegers(getFavouritesQuestionsList());
    }

    private  int[] convertIntegers(ArrayList<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}
