package com.make.projects.model.dto.lookup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tech {

    private String techItem;

    @Override
    public String toString() {
        return "Tech{" +
                "techItem='" + techItem + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tech tech = (Tech) o;
        return Objects.equals(getTechItem(), tech.getTechItem());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTechItem());
    }
}
