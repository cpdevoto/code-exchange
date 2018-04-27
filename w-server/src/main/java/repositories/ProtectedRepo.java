package repositories;

import javax.inject.Singleton;


@Singleton
public class ProtectedRepo {

  private final UserDao userDao;
  private final NoticeDao noticeDao;
  private final MerchantDao merchantDao;
  private final SearchDao searchDao;

  public ProtectedRepo() {
    this.userDao = new UserDao();
    this.noticeDao = new NoticeDao();
    this.merchantDao = new MerchantDao();
    this.searchDao = new SearchDao();
  }

  public UserDao users() {
    return userDao;
  }

  public NoticeDao notices() {
    return noticeDao;
  }

  public MerchantDao merchants() {
    return merchantDao;
  }

  public SearchDao search() {
    return searchDao;
  }
}
