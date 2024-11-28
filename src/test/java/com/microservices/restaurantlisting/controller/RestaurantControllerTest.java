package com.microservices.restaurantlisting.controller;

import com.microservices.restaurantlisting.dto.RestaurantDTO;
import com.microservices.restaurantlisting.mapper.RestaurantMapper;
import com.microservices.restaurantlisting.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

public class RestaurantControllerTest {

    @InjectMocks
    RestaurantController restaurantController;
    @Mock
    RestaurantService restaurantService;

   @BeforeEach
   public void setUp(){
       MockitoAnnotations.openMocks(this);
   }

    @Test
    public void testFetchAllRestaurants(){
        //Arrange the data
        List<RestaurantDTO> allResturants = Arrays.asList(
                new RestaurantDTO(1,"Restaurant 1","address1","city1","restauranr description1"),
                new RestaurantDTO(2,"Restaurant 2","address2","city2","restauranr description2"),
                new RestaurantDTO(3,"Restaurant 3","address3","city3","restauranr description3"));

        when(restaurantService.fetchAllRestaurants()).thenReturn(allResturants);

        //Act call actual method of controller to test
        ResponseEntity<List<RestaurantDTO>> respone = restaurantController.fetchAllRestaurants();
        //Assert : check the actual and expected result

        assertEquals(HttpStatus.OK,respone.getStatusCode());
        assertEquals(allResturants,respone.getBody());

        // verify the service that is called
        verify(restaurantService,times(1)).fetchAllRestaurants();
    }

    @Test
    public void testAddRestaurant(){

       RestaurantDTO restaurantDTO = new RestaurantDTO(1,"Restaurant 1","address1","city1","restauranr description1");
       when(restaurantService.addRestaurantInDB(restaurantDTO)).thenReturn(restaurantDTO);
       ResponseEntity<RestaurantDTO> responseEntity = restaurantController.addRestaurant(restaurantDTO);
       assertEquals(restaurantDTO,responseEntity.getBody());
       assertEquals(HttpStatus.CREATED,responseEntity.getStatusCode());
       verify(restaurantService,times(1)).addRestaurantInDB(restaurantDTO);
    }


    @Test
    public void testFetchRestaurantById(){
        Integer mockedRestaurantId = 1;
        RestaurantDTO restaurantDTO = new RestaurantDTO(1,"Restaurant 1","address1","city1","restauranr description1");
        when(restaurantService.fetchRestaurantById(1)).thenReturn(new ResponseEntity<>(restaurantDTO,HttpStatus.OK));
        ResponseEntity<RestaurantDTO> responseEntity = restaurantController.fetchRestaurantById(mockedRestaurantId);
        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals(restaurantDTO,responseEntity.getBody());
        verify(restaurantService,times(1)).fetchRestaurantById(mockedRestaurantId);
    }
}
