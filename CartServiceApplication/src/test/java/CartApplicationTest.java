//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.infy.dto.CartDTO;
//import com.infy.dto.ItemDTO;
//import com.infy.entity.Cart;
//import com.infy.exception.CartException;
//import com.infy.repository.CartRepository;
//import com.infy.service.CartServiceImpl;
//
//@ExtendWith(MockitoExtension.class)
//public class CartApplicationTest {
//
//	@InjectMocks
//	CartServiceImpl cartServiceImpl;
//	
//	@Mock
//	private CartRepository cartRepository;
//	
//	@Test
//	public void TestaddCart() throws CartException {
//		CartDTO cartDTO = new CartDTO();
//		 Cart cart = new Cart();
//		 
//			List<ItemDTO> itemDTOList = new ArrayList<>();
//			List<Cart> cartList = new ArrayList<>();
//			Cart cart = new Cart();
//			cart.setUserId(222);
//			
//				ItemDTO itemDTO1 = new ItemDTO();
//				itemDTO1.setCartId("1");
//				itemDTO1.setProductId(234567L);
//				itemDTO1.setName("mouse");
//				itemDTO1.setQuantity(3);
//				itemDTO1.setPrice(20.0);
//				itemDTO1.setTotalPrice(60.0);
//				
//				itemDTOList.add(itemDTO1); 
//				
//				ItemDTO itemDTO2 = new ItemDTO();
//				itemDTO2.setCartId("2");
//				itemDTO2.setProductId(123456L);
//				itemDTO2.setName("laptop");
//				itemDTO2.setQuantity(2);
//				itemDTO2.setPrice(30.0);
//				itemDTO2.setTotalPrice(60.0);
//				
//				itemDTOList.add(itemDTO2);  
//			
//			cartDTO.setUserId("222");
//			
//			cartDTO.setItems(itemDTOList);
//			cartDTO.setTotalItems(6);
//			cartDTO.setTotalPrice(120.0);
//			Mockito.when(cartRepository.findByUserId(Mockito.anyInt(),Mockito.anyInt())).thenReturn(bookList);
//			Assertions.assertEquals(bookDTOList.size(), cartServiceImpl.findByUserId("TestBook", "testAuthor").size());
//	}
//	
//	
//	
//}





//List<Cart> cartlist = cartRepository.findByUserId(Integer.parseInt(cartDeleteDTO.getUserId()));
//		if(cartlist.isEmpty()) {
//			throw new CartException(ExceptionConstants.CART_NOT_FOUND.toString());
//		}
//		cartRepository.deleteAll(cartlist);
//		return "message"+":"+"Cart cleared successfully";
