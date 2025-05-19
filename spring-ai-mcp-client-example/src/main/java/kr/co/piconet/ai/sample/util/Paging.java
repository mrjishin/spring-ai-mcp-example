/**
 * Copyright 2025 Jaeik Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kr.co.piconet.ai.sample.util;

import lombok.ToString;

@ToString
public class Paging
{
	private static final int DEFAULT_BLOCK_LIMIT = 10;
	private static final int DEFAULT_LIMIT = 10;

	private int blockLimit = DEFAULT_BLOCK_LIMIT;
	private int limit = DEFAULT_LIMIT;
	private long total;
	private long page;
	private long startPage = 1L;
	private long lastPage;
	private long prevPage;
	private long nextPage;
	private long block;
	private long lastBlock;
	private long prevBlock;
	private long nextBlock;
	private long blockStartPage;
	private long blockLastPage;
	private long prevBlockStartPage;
	private long nextBlockStartPage;
	
	public static Paging of(long total, long page, int limit) {
		return of(total, page, DEFAULT_BLOCK_LIMIT, limit);
	}
	public static Paging of(long total, long page, int blockLimit, int limit) {
		return new Paging(total, page, blockLimit, limit);
	}
	private Paging(long total, long page, int blockLimit, int limit) {
		this.total = total;
		this.page = page;
		this.blockLimit = blockLimit;
		this.limit = limit;

		setLastPage(calcLastPage());
		setPrevPage(calcPrevPage());
		setNextPage(calcNextPage());
		setLastBlock(calcLastBlock());
		setBlock(calcBlock());
		setPrevBlock(clacPrevBlock());
		setNextBlock(calcNextBlock());
		setBlockStartPage(calcBlockStartPage());
		setBlockLastPage(calcBlockLastPage());
		setPrevBlockStartPage(calcPrevBlockStartPage());
		setNextBlockStartPage(calcNextBlockStartPage());
		
		if(this.page > this.lastPage)
			this.page = this.getLastPage();
	}
	private long calcLastPage() {
		if(getTotal() <= 0L) return 0L;
		return (long)Math.ceil(getTotal() * 1.0D / getLimit());
	}
	private long calcPrevPage() {
		if(getPage() - 1 <= 0)
			return 1L;
		return getPage() -1;
	}
	private long calcNextPage() {
		if(getPage() + 1 >= getLastPage())
			return getLastPage();
		return getLastPage() + 1;
	}
	private long calcLastBlock() {
		return (long)Math.ceil(getLastPage() * 1.0D / getBlockLimit());
	}
	private long calcBlock() {
		return (long)Math.ceil(getPage() * 1.0D / getBlockLimit());
	}
	private long clacPrevBlock() {
		if(getBlock() - 1 <= 0)
			return 1L;
		return getBlock() - 1;
	}
	private long calcNextBlock() {
		if(getBlock() + 1 >= getLastBlock())
			return getLastBlock();
		return getBlock() + 1;
	}
	private long calcBlockStartPage() {
		return (getBlock() - 1) * getBlockLimit() + 1;
	}
	private long calcBlockLastPage() {
		long bLastPage = getBlock() * getBlockLimit();
		if(bLastPage > getLastPage())
			bLastPage = getLastPage();
		return bLastPage;
	}
	public long calcPrevBlockStartPage() {
		return getPrevBlock() * blockLimit;
	}
	public long calcNextBlockStartPage() {
		return getNextBlock() * blockLimit - blockLimit + 1;
	}

	/**
	 * Getter, Setter
	 */
	public long getStartRow() {
		if(total == 0)
			return 0L;
		else
			return (this.page * limit) - limit;
	}
	public int getBlockLimit() {
		return blockLimit;
	}
	public void setBlockLimit(int blockLimit) {
		this.blockLimit = blockLimit;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getPage() {
		return page;
	}
	public void setPage(long page) {
		this.page = page;
	}
	public long getStartPage() {
		return startPage;
	}
	public void setStartPage(long startPage) {
		this.startPage = startPage;
	}
	public long getLastPage() {
		return lastPage;
	}
	public void setLastPage(long lastPage) {
		this.lastPage = lastPage;
	}
	public long getPrevPage() {
		return prevPage;
	}
	public void setPrevPage(long prevPage) {
		this.prevPage = prevPage;
	}
	public long getNextPage() {
		return nextPage;
	}
	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}
	public long getBlock() {
		return block;
	}
	public void setBlock(long block) {
		this.block = block;
	}
	public long getLastBlock() {
		return lastBlock;
	}
	public void setLastBlock(long lastBlock) {
		this.lastBlock = lastBlock;
	}
	public long getPrevBlock() {
		return prevBlock;
	}
	public void setPrevBlock(long prevBlock) {
		this.prevBlock = prevBlock;
	}
	public long getNextBlock() {
		return nextBlock;
	}
	public void setNextBlock(long nextBlock) {
		this.nextBlock = nextBlock;
	}
	public long getBlockStartPage() {
		return blockStartPage;
	}
	public void setBlockStartPage(long blockStartPage) {
		this.blockStartPage = blockStartPage;
	}
	public long getBlockLastPage() {
		return blockLastPage;
	}
	public void setBlockLastPage(long blockLastPage) {
		this.blockLastPage = blockLastPage;
	}
	public long getPrevBlockStartPage() {
		return prevBlockStartPage;
	}
	public void setPrevBlockStartPage(long prevBlockStartPage) {
		this.prevBlockStartPage = prevBlockStartPage;
	}
	public long getNextBlockStartPage() {
		return nextBlockStartPage;
	}
	public void setNextBlockStartPage(long nextBlockStartPage) {
		this.nextBlockStartPage = nextBlockStartPage;
	}
}