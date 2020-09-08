package sb.domain.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sb.domain.entity.LineItem;
import sb.domain.model.LineItemModel;
import sb.persistence.dao.LineItemDAO;
import sb.persistence.dao.ProductDAO;

import java.util.HashMap;

@Component
public class LineItemMapper {

    private LineItemDAO lineItemDAO;
    private ProductDAO productDAO;


    public LineItemMapper(LineItemDAO lineItemDAO, ProductDAO productDAO) {
        this.lineItemDAO = lineItemDAO;
        this.productDAO = productDAO;
    }

    public LineItemModel toModel(LineItem li) {
        HashMap<String, Integer> productQuantity = new HashMap<>();
        for(LineItem lineItem : lineItemDAO.getAll()){
            productQuantity.put(productDAO.get(lineItem.getProductId()).getName(),
                                lineItem.getQuantity());
        }
        return new LineItemModel(productQuantity);
    }
}
