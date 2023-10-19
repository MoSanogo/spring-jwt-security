package com.mosan.mosan.rest.jwtoken.jsonviews;

public class View {
   public interface UserView{
       //External view for User .
       interface  External{}
       //Internal  view for User will inherit all fields in External.
       interface Internal  extends External{}
    }
}
