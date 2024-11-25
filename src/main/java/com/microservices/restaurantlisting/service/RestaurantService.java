package com.microservices.restaurantlisting.service;

import com.microservices.restaurantlisting.dto.RestaurantDTO;
import com.microservices.restaurantlisting.entity.Restaurant;
import com.microservices.restaurantlisting.mapper.RestaurantMapper;
import com.microservices.restaurantlisting.repo.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;


    public List<RestaurantDTO> fetchAllRestaurants() {
       List<Restaurant> restaurantList = restaurantRepository.findAll();
       List<RestaurantDTO> restaurantDTOList  = restaurantList.stream()
                .map(restaurant -> RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(restaurant))
                .collect(Collectors.toList());
        return restaurantDTOList;
    }

    public RestaurantDTO addRestaurantInDB(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.save(RestaurantMapper.INSTANCE.getRestaurantFromRestaurantDTO(restaurantDTO));
        return RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(restaurant);
    }

    public ResponseEntity<RestaurantDTO> fetchRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if(restaurant.isPresent())
            return new ResponseEntity<>(RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(restaurant.get()),HttpStatus.OK);
        else
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

