package com.singh.ecommerceapp.product.mapper;

import com.singh.ecommerceapp.product.entity.Category;
import com.singh.ecommerceapp.product.entity.Product;
import com.singh.ecommerceapp.product.entity.Tag;
import com.singh.ecommerceapp.product.model.dto.product.CreateProductRequest;
import com.singh.ecommerceapp.product.model.dto.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface IProductMapper {

    @Mapping(target = "category", expression = "java(mapCategories(createProductRequest.getCategory()))")
    @Mapping(target = "tags", expression = "java(mapTags(createProductRequest.getTags()))")
    Product toProduct(CreateProductRequest createProductRequest);


    @Mapping(target = "category", expression = "java(mapCategoryNames(product.getCategory()))")
    @Mapping(target = "tags", expression = "java(mapTagNames(product.getTags()))")
    ProductResponse toProductResponse(Product product);

    default List<String> mapCategoryNames(List<Category> categories) {
        if (categories == null) return null;
        return categories.stream().map(Category::getCategoryName).toList();
    }

    default List<String> mapTagNames(List<Tag> tags) {
        if (tags == null) return null;
        return tags.stream().map(Tag::getName).toList();
    }

    default List<Category> mapCategories(List<String> categoryNames) {
        if (categoryNames == null) return null;
        return categoryNames.stream().map(name -> {
            Category category = new Category();
            category.setCategoryName(name);
            return category;
        }).collect(Collectors.toList());
    }

    default List<Tag> mapTags(List<String> tagNames) {
        if (tagNames == null) return null;
        return tagNames.stream().map(name -> {
            Tag tag = new Tag();
            tag.setName(name);
            return tag;
        }).collect(Collectors.toList());
    }
}