package com.emrizkiem.registersmi.utils;

import androidx.recyclerview.widget.DiffUtil;

import com.emrizkiem.registersmi.data.model.Users;

import java.util.List;

public class UsersDiffCallback extends DiffUtil.Callback {
    private final List<Users> oldList;
    private final List<Users> newList;

    public UsersDiffCallback(List<Users> oldList, List<Users> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Users old = oldList.get(oldItemPosition);
        final Users news = newList.get(newItemPosition);

        return old.getUsername().equals(news.getUsername()) && old.getMotivationLetter().equals(news.getMotivationLetter());
    }
}
