package com.microservices.restaurantlisting.mapper;

import com.microservices.restaurantlisting.dto.RestaurantDTO;
import com.microservices.restaurantlisting.entity.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RestaurantMapper {

    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);

    Restaurant getRestaurantFromRestaurantDTO (RestaurantDTO restaurantDTO);
    RestaurantDTO getRestaurantDTOFromRestaurant(Restaurant restaurant);

}
