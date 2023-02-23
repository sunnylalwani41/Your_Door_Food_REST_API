package com.masai.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "categoryGenrator")
    @SequenceGenerator(name = "categoryGenrator",sequenceName = "catgen",allocationSize = 1,initialValue = 101)
	private Integer catId;
	private String categoryName;
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "category" ,fetch = FetchType.EAGER)
	private List<Item> item;
}
