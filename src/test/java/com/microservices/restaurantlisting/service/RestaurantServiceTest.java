package com.microservices.restaurantlisting.service;

import com.microservices.restaurantlisting.dto.RestaurantDTO;
import com.microservices.restaurantlisting.entity.Restaurant;
import com.microservices.restaurantlisting.mapper.RestaurantMapper;
import com.microservices.restaurantlisting.repo.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    RestaurantService restaurantService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        //in order for Mock and InjectMocks annotations to take effect, you need to call MockitoAnnotations.openMocks(this);
    }

    @Test
    public  void testFetchAllRestaurants(){
        List<Restaurant> mockedRestaurants = Arrays.asList(
                new Restaurant(1,"Restaurant 1","address1","city1","restauranr description1"),
                new Restaurant(2,"Restaurant 2","address2","city2","restauranr description2"),
                new Restaurant(3,"Restaurant 3","address3","city3","restauranr description3"));
        when(restaurantRepository.findAll()).thenReturn(mockedRestaurants);
        List<RestaurantDTO> restaurantDTOList = restaurantService.fetchAllRestaurants();
       assertEquals(mockedRestaurants.size(),restaurantDTOList.size());
       for(int i =0 ; i<mockedRestaurants.size() ; i++){
           RestaurantDTO restaurantDTO =  RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(mockedRestaurants.get(i));
           assertEquals(restaurantDTO,restaurantDTOList.get(i));
       }
        verify(restaurantRepository,times(1)).findAll();
    }


    @Test
    public void testAddRestaurantInDB(){
        Restaurant mockedRestaurant = new Restaurant(1,"Restaurant 1","address1","city1","restaurant description1");
        RestaurantDTO mockedrestaurantDTO = RestaurantMapper.INSTANCE.getRestaurantDTOFromRestaurant(mockedRestaurant);
        when(restaurantRepository.save(mockedRestaurant)).thenReturn(mockedRestaurant);
        RestaurantDTO restaurantDTO = restaurantService.addRestaurantInDB(mockedrestaurantDTO);
        assertEquals(mockedrestaurantDTO,restaurantDTO);
        verify(restaurantRepository,times(1)).save(mockedRestaurant);
    }

    @Test
    public void testFetchRestaurantById_ExistingId(){
        Integer mockedRestaurantId = 1;
        Restaurant mockedRestaurant = new Restaurant(1,"Restaurant 1","address1","city1","restaurant description1");
        when(restaurantRepository.findById(mockedRestaurantId)).thenReturn(Optional.of(mockedRestaurant));
        ResponseEntity<RestaurantDTO> responseEntity = restaurantService.fetchRestaurantById(mockedRestaurantId);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(mockedRestaurantId,responseEntity.getBody().getId());
        verify(restaurantRepository,times(1)).findById(mockedRestaurantId);
    }

    @Test
    public void testFetchRestaurantById_NonExistingId(){
        Integer mockedRestaurantId = 1;
        when(restaurantRepository.findById(mockedRestaurantId)).thenReturn(Optional.empty());
        ResponseEntity<RestaurantDTO> responseEntity = restaurantService.fetchRestaurantById(mockedRestaurantId);
        assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
        assertEquals(null,responseEntity.getBody());
        verify(restaurantRepository,times(1)).findById(mockedRestaurantId);
    }
}
