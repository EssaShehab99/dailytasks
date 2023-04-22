package com.example.dailytasks.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class User {
  public String id;
  public String name;
  public String email;
  public String password;
  public int image;
  public int color;

    public User(String id, String name, String email,String password, int image, int color) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.image = image;
        this.color = color;
    }

    public User() {

    }

    public User fromMap(Map<String, Object> map, String id) {
        return new User(
                id,
                Objects.requireNonNull(map.getOrDefault("name","")).toString(),
                Objects.requireNonNull(map.getOrDefault("email","")).toString(),
                "",
                Integer.parseInt(Objects.requireNonNull(map.getOrDefault("image","")).toString()),
                Integer.parseInt(Objects.requireNonNull(map.getOrDefault("color","")).toString())
        );
    }

    public final Map<String, Object> toMap() {
        return new HashMap<String, Object>() {{
            put("name", name);
            put("email", email);
            put("password", password);
            put("image", image);
            put("color", color);
        }};
    }
}
