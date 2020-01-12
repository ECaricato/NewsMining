package API;

import Entities.Page;

public interface ICRUDPage {
    boolean insertPage();
    boolean deletePage();
    Page getPageByTitle();
}
